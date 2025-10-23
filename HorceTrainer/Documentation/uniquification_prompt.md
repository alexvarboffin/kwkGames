# Prompt: How to Uniquify the Application

## Objective

Your task is to uniquify the application by modifying various assets and styles. This includes fonts, color themes, gradients, images, and emojis.

---

## 1. Font Uniquification

### Objective

Apply all custom fonts found in the project to the entire Jetpack Compose application by setting a default font family in the app's theme. A key requirement is to ensure the font application is **unique** with each execution of this prompt.

### Instructions

1.  **Locate Custom Fonts:** Identify the custom font files in `src/main/res/font`.
2.  **Locate Theme Definition Files:** Find `Theme.kt` and `Type.kt` in `src/main/java/com/package/name/ui/theme/`.
3.  **Define a Unique `FontFamily` in `Type.kt`:** Randomly assign the located fonts to different `FontWeight`s (e.g., `Normal`, `Bold`, `Medium`).
4.  **Define `Typography` in `Type.kt`:** Define a `Typography` object that uses the unique `AppFontFamily`.
5.  **Apply `Typography` in `Theme.kt`:** Apply the new `AppTypography` to the `MaterialTheme`.

---

## 2. Premium Color Theme Uniquification

### Objective

Transform the application's existing color scheme into a unique, "premium" theme. This involves defining a more sophisticated color palette and applying it, with special attention to making simple components like buttons look more polished using gradients.

### Instructions

1.  **Analyze the Existing Theme:** Analyze `Color.kt` and `Theme.kt`.
2.  **Define a New "Premium" Palette:** Choose sophisticated colors and define them in `Color.kt`.
3.  **Apply the New Palette to the Theme:** Modify `Theme.kt` to use the new premium colors in the `ColorScheme`.
4.  **Apply Premium Styles to Components:** Apply gradients and other styles to components like buttons.

---

## 3. Gradient Uniquification

### Objective

Ensure all gradients in the application are unique and not hardcoded.

### Instructions

1.  **Find Hardcoded Gradients:** Search the codebase for any hardcoded gradients.
2.  **Replace with Unique Colors:** Replace the hardcoded colors with a new, unique set of colors.

---

## 4. Image Redesign

### Objective

Generate new, unique visuals for the application's background and splash screen images without using the original images as a base.

### Prompts for AI Image Generation

*   **Game Background (replaces `ic_game_background.jpg`):** "A breathtaking, ultra-wide panoramic digital painting of a packed cricket stadium at dusk. The sky is a dramatic mix of deep purples, oranges, and blues. The stadium lights are just turning on, casting long, dramatic shadows on the perfectly manicured green field. The crowd is a blur of motion and color, creating a sense of immense energy. The style should be modern, clean, and slightly stylized, with a focus on dramatic lighting and atmosphere. No text or logos should be visible. The perspective should be from a high vantage point behind the batsman, in the style of Unreal Tournament '99."
*   **Level Select Background (replaces `ic_level_select_background.jpg`):** "A top-down, hand-drawn illustration of a vintage-style treasure map for a cricket adventure. The background is a textured parchment paper. A dotted line path winds across the map, connecting different cricket-themed landmarks that represent the levels: a crossed pair of cricket bats, a cricket helmet, a wicket, a trophy, etc. The title 'Level Select' is written in a playful, hand-drawn font at the top. The overall style should be charming, whimsical, and slightly rustic, with a warm, inviting color palette, in the style of Unreal Tournament '99."
### 3. Main Menu Background (replaces `ic_main_background.jpg`)

**Objective:** Create a dynamic and high-tech background for the game's main menu that is visually striking.

**Prompt:**
"A dramatic, high-tech sports background for a cricket game's main menu. The focus is a macro shot of a realistic, brand-new leather cricket ball, positioned off-center. Abstract, glowing blue and gold energy streaks and particles flow around the ball, suggesting speed, data, and technology. The background is a very dark, out-of-focus stadium scene at night, creating a strong depth of field. The overall mood is energetic, premium, and futuristic, in the style of Unreal Tournament '99."
*   **Splash Screen (replaces `ic_splashscreen.png`):** "An epic, action-focused splash screen for a cricket game titled 'ARENA MSTB'. A powerful fast bowler is captured in a dynamic, mid-action pose, just as the ball leaves his hand, with explosive energy and motion blur. The scene is set in a packed stadium at night, with dramatic, cinematic lighting. The game title, 'ARENA MSTB', should be rendered in a bold, modern, metallic 3D font, integrated into the scene with sparks and light effects. The style should be hyper-realistic and intense, similar to a modern video game cover, in the style of Unreal Tournament '99."

---

## 5. Emoji Uniquification

### Objective

Uniquify the list of emojis used in the game.

### Instructions

1.  **Locate Emoji List:** Find the list of emojis in the codebase.
2.  **Replace with Unique Emojis:** Replace the existing emojis with a new, unique set of emojis that are relevant to the game's theme.