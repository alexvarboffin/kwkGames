#!/usr/bin/env bash
set -euo pipefail
MAX_ITER=${1:-10}
THRESHOLD=${2:-0.95}
REF="./html5/reference.png"
ART="artifacts"
mkdir -p "$ART"
for i in $(seq 1 $MAX_ITER); do
  echo "=== ITERATION $i ==="
  echo "{\"iteration\":$i, \"ts\":\"$(date --iso-8601=seconds)\"}" > "$ART/iteration_${i}.json"

  # build
  ./gradlew assembleDebug > "$ART/build_iter_${i}.log" 2>&1 || {
    echo "BUILD FAILED on iter $i — check $ART/build_iter_${i}.log"
    # agent должен сгенерировать patch: artifacts/patch_iter_${i}.patch
    exit 1
  }

  APK="app/build/outputs/apk/debug/app-debug.apk"
  if command -v adb >/dev/null 2>&1 && adb devices | grep -q device; then
    adb install -r "$APK" > "$ART/adb_install_iter_${i}.log" 2>&1 || echo "adb install failed" >> "$ART/adb_install_iter_${i}.log"
    adb shell am start -n com.esportedasorte.esportefootbal/.MainActivity || true
    adb shell screencap -p /sdcard/screen.png
    adb pull /sdcard/screen.png "$ART/screen_iter_${i}.png"
  else
    echo "ADB not available or no device; simulating screenshot..." > "$ART/adb_install_iter_${i}.log"
    # Agent должны поместить сюда локально сгенерированный эмуляторный снимок: artifacts/screen_iter_${i}.png
  fi

  python3 scripts/compare_images.py "$REF" "$ART/screen_iter_${i}.png" "$ART/compare_iter_${i}.json"
  SIM=$(jq -r .ssim "$ART/compare_iter_${i}.json")
  echo "SSIM=$SIM"
  if (( $(echo "$SIM >= $THRESHOLD" | bc -l) )); then
    echo "SUCCESS on iter $i"
    exit 0
  else
    echo "Not good enough — agent must produce patch artifacts/patch_iter_${i}.patch and continue"
    # Agent обязан модифицировать код прямо сейчас и повторить итерацию
  fi
done
echo "Max iterations reached"
exit 2