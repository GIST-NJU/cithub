echo Running Script_1 deploy.bat 
:: 前端
@echo off
:: 获取当前脚本文件所在目录
set SCRIPT_DIR=%~dp0

:: 设置目标文件夹路径为脚本文件上一级目录的 cithub-deploy 文件夹
set TARGET_DIR=%SCRIPT_DIR%..\cithub-deploy

:: 输出目标文件夹路径
echo Target directory is %TARGET_DIR%

:: 如果 cithub-deploy 文件夹不存在，则创建它
if not exist "%TARGET_DIR%" (
    echo Creating target directory...
    mkdir "%TARGET_DIR%"
)

:: 如果 cithub-deploy 文件夹内的 dist 文件夹不存在，则创建它
if not exist "%TARGET_DIR%\dist" (
    echo Creating dist directory inside target directory...
    mkdir "%TARGET_DIR%\dist"
)

:: 切换到同级文件夹 Frontend
cd /d "%SCRIPT_DIR%Frontend"

:: 输出当前目录
echo Current directory is %cd%

:: 执行 npm run build 命令
echo Running npm run build...
call npm run build

:: 检查 npm run build 的退出代码
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b %errorlevel%
)

:: 切换到 Frontend 的 dist 文件夹
cd dist

:: 输出当前目录
echo Current directory after cd to dist is %cd%

:: 复制 dist 文件夹内的所有文件到目标文件夹的 dist 文件夹
echo Copying files to %TARGET_DIR%\dist...
xcopy * "%TARGET_DIR%\dist" /s /e /y

:: 显示完成信息
echo Files copied to %TARGET_DIR%\dist

::后端

:: 切换到脚本同级的文件夹 Backend
cd /d "%SCRIPT_DIR%Backend"

:: 将后端项目打包为jar包
call mvn clean package -Dmaven.test.skip=true

::先删除已有的docker image
call docker rmi  xiangjt/cithubdeploy:latest 

::将jar包打包为docker image
call docker build -t xiangjt/cithubdeploy:latest .

::将docker image压缩为tar包
call docker save -o cithubdeploy.tar xiangjt/cithubdeploy:latest

:: 复制 cithubdeploy.tar 到目标文件夹的 cithub-deploy 文件夹
echo Copying cithubdeploy.tar to %TARGET_DIR%...
copy cithubdeploy.tar "%TARGET_DIR%"

:: 切换到 cithub-deploy 文件夹
cd /d "%TARGET_DIR%"

:: 获取当前日期和时间
for /f "tokens=1-3 delims=/ " %%a in ('date /t') do (
    set "mm=%%a"
    set "dd=%%b"
    set "yy=%%c"
)
for /f "tokens=1-3 delims=: " %%a in ('time /t') do (
    set "hh=%%a"
    set "min=%%b"
    set "sec=%%c"
)

:: 将日期和时间格式化为适合作为提交消息的格式
set datetime=%yy%%mm%%dd%_%hh%%min%%sec%

:: 执行 git add .
call git add .

:: 执行 git commit -m "update" + 当前日期和时间
call git commit -m "update %datetime%"

:: 执行 git push gist-cithub
call git push gist-cithub

echo deploy success!
pause
