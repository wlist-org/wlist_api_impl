# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed

* 重命名 register 方法

## [1.2.0] - 2024-3-3

### Added

* 添加登出接口

### Changed

* 将 userId 类型改为 String
* 移动 common/exceptions 到子包
* 拆分登录与注册接口分类

## [1.1.0] - 2024-2-21

### Added

* 添加命中频控异常
* 添加关闭 native 线程接口
* 添加检查更新接口

### Changed

* 将所有内部构造器接口改为 private/protected

## [1.0.1] - 2024-2-20

### Added

* 支持检查 Client 是否不再可用

### Fixed

* 修复构造器编译错误

## [1.0.0] - 2024-2-19

### Added

* 实现 common/core/web 基础功能
