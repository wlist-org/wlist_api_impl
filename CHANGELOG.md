# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.6.0] - 2024-3-28

### Added

* 添加 initialize 接口

## [1.5.2] - 2024-3-27

### Changed

* CoreServer 不校验 WebToken 正确性

## [1.5.1] - 2024-3-26

### Changed

* 补齐 Recyclable 接口

### Fixed

* 修正 checkName 参数 storage 的类型错误

## [1.5.0] - 2024-3-26

### Added

* 添加分享文件和文件夹接口

### Changed

* 使用 Instant 代替 ZonedDateTime
* 优化文档

## [1.4.0] - 2024-3-24

### Added

* 添加获取文件的缩略图接口
* 添加恢复回收站文件接口
* 添加列出回收站文件接口

### Changed

* 简化 CoreClient 实现

## [1.3.0] - 2024-3-14

### Changed

* *Modified Stable* 将 Version 中的 CompletableFuture 改为 NetworkFuture

## [1.2.2] - 2024-3-10

### Added

* 添加注销 unregister 接口

### Changed

* 优化文档
* 添加 web api 长度限制

## [1.2.1] - 2024-3-5

### Changed

* 重命名 register 方法
* 优化文档

## [1.2.0] - 2024-3-3

### Added

* 添加登出 logout 接口

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
