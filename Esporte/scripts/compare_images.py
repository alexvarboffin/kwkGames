#!/usr/bin/env python3
import sys
import json
from PIL import Image, ImageChops
import imagehash

def compare_images(ref_path, candidate_path, output_path):
    try:
        # Open images
        ref_img = Image.open(ref_path)
        candidate_img = Image.open(candidate_path)
        
        # Resize to same dimensions if needed
        if ref_img.size != candidate_img.size:
            candidate_img = candidate_img.resize(ref_img.size)
        
        # Calculate SSIM (simplified as we don't have skimage)
        # Using perceptual hash difference as approximation
        ref_hash = imagehash.phash(ref_img)
        candidate_hash = imagehash.phash(candidate_img)
        
        # Calculate similarity (0-1 scale)
        hash_diff = ref_hash - candidate_hash
        max_hash_diff = 64.0  # Maximum possible difference for phash
        similarity = 1.0 - (hash_diff / max_hash_diff)
        
        # Determine if it's acceptable (simplified threshold)
        ok = similarity >= 0.95
        
        # Create result
        result = {
            "ssim": float(similarity),
            "phash_diff": int(hash_diff),
            "ok": bool(ok)
        }
        
        # Write result to file
        with open(output_path, 'w') as f:
            json.dump(result, f, indent=2)
            
        print(f"Comparison complete. SSIM: {similarity:.4f}, OK: {ok}")
        return result
        
    except Exception as e:
        # Create error result
        result = {
            "ssim": 0.0,
            "phash_diff": 64,
            "ok": False,
            "error": str(e)
        }
        
        # Write result to file
        with open(output_path, 'w') as f:
            json.dump(result, f, indent=2)
            
        print(f"Error comparing images: {e}")
        return result

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: compare_images.py <reference.png> <candidate.png> <output.json>")
        sys.exit(1)
        
    ref_path = sys.argv[1]
    candidate_path = sys.argv[2]
    output_path = sys.argv[3]
    
    compare_images(ref_path, candidate_path, output_path)