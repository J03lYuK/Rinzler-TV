# Rinzler fresh-start notes

This source tree has been cleaned into a new **Rinzler** baseline from the uploaded AndroidTV-FireTV source.

## Main changes already applied
- App name changed to **Rinzler**
- Debug app name changed to **Rinzler Debug**
- Android application ID changed to **uk.rinzler.tv**
- Core Moonfin package references changed to **uk.rinzler**
- README, privacy policy, and store text updated to Rinzler branding
- Buy Me a Coffee link updated to **buymeacoffee.com/kylejoel87e**
- GitHub references pointed at **J03lYuK/Rinzler-TV**

## Things still worth doing manually
- Replace launcher, banner, and channel artwork with final Rinzler assets
- Review every user-facing string for any remaining Moonfin references
- Check plugin wording if you want to rename the server plugin too
- Build once locally and fix any compile errors caused by upstream package assumptions

## Suggested first build
```bash
./gradlew assembleDebug
```

## Suggested Git steps
```bash
git init
git add .
git commit -m "Initial Rinzler baseline"
```
