@echo off

REM Run Maven Clean
call mvn clean
if %errorlevel% neq 0 (
    echo Maven clean failed.
    exit /b %errorlevel%
)

REM Run Maven Install
call mvn install
if %errorlevel% neq 0 (
    echo Maven install failed.
    exit /b %errorlevel%
)

REM Run Docker Compose Up
call docker compose up --build
if %errorlevel% neq 0 (
    echo Docker Compose up failed.
    exit /b %errorlevel%
)
