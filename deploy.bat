A@echo off
echo Building Beautiful Struggle Foundation Web Platform...
call gradlew.bat :app:wasmJsBrowserDistribution

if %ERRORLEVEL% EQU 0 (
    echo Build Successful!
    echo.
    echo Next Step: Deploying to Cloudflare Pages...
    echo (Make sure you have installed wrangler: npm install -g wrangler)
    npx wrangler pages deploy app/build/dist/wasmJs/productionExecutable --
    project-name beautiful-struggle-foundation
) else (
    echo Build Failed. Please check the errors above.
    pause
)
