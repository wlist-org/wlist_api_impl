# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.18.0] - 2024-6-12

### Changed

* 优化 Refresh 注释
* 标记 core 中 Refresh 为 Stable

### Added

* 添加 Refresh 示例
* Refresh 添加 isPaused 方法

## [1.17.2] - 2024-5-31

### Changed

* add storage 时返回 StorageInformation

## [1.17.1] - 2024-5-23

### Changed

* Token 添加 storage 字段

## [1.17.0] - 2024-5-22

### Changed

* 将 StorageType 相关方法移动到基类

### Added

* 添加 minFilenameLength 限制条件

## [1.16.1] - 2024-5-16

### Changed

* 优化 checkName 参数

## [1.16.0] - 2024-5-15

### Changed

* 优化 DownloadInformation 文档

### Removed

* 移除 NetworkSendingEvent 和 NetworkReceivingEvent

## [1.15.1] - 2024-5-15

### Changed

* FileInformation 和 TrashInformation 中 time 系列字段可能为 unknown

## [1.15.0] - 2024-5-7

### Changed

* 上传下载支持暂停

### Added

* 添加 trash refresh API

## [1.14.0] - 2024-5-5

### Changed

* refresh 操作可以 check 结束状态
* restore 操作需指定父文件夹

## [1.13.1] - 2024-4-21

### Added

* 添加 trashes 搜索接口

## [1.13.0] - 2024-4-20

### Changed

* 合并使用同一个 Direction 类

### Added

* 独立 trashes 分类
* 添加彻底删除回收站文件接口

## [1.12.2] - 2024-4-18

### Changed

* 优化 Files 行为
* 标记 core 中 Storage 为 Stable

## [1.12.1] - 2024-4-14

### Added

* 支持按照文件后缀名排序

### Changed

* 更新 Readme

## [1.12.0] - 2024-4-8

### Added

* 添加搜索 API
* 支持更多 storage 排序选项

### Changed

* 添加 rootDirectoryId

## [1.11.1] - 2024-4-7

### Changed

* 优化 Exception

## [1.11.0] - 2024-4-7

### Added

* 通过 StoragesType 快速预查 filename 是否合法

### Changed

* 优化 Exception

## [1.10.1] - 2024-4-6

### Changed

* 优化 StorageDetailsInformation 流量统计

## [1.10.0] - 2024-4-6

### Changed

* 优化 checkConfig 行为
* 优化 core 中部分 exceptions

## [1.9.0] - 2024-4-5

### Added

* Storages 支持 checkConfig

### Changed

* 优化 Files 中 checkName 文档
* 优化 InvalidStorageConfigException 错误信息

## [1.8.0] - 2024-4-2

### Added

* 分享支持自定义过期时间
* 添加详细的 StorageDetailsInformation

### Changed

* 上传需要计算分片 MD5

## [1.7.3] - 2024-3-28

### Changed

* 移动 web 中异常到 common
* 优化文档
* 标记 core 中 Client 及 Server 相关方法为 Stable

## [1.7.2] - 2024-3-28

### Changed

* 所有自定义异常类改为 RuntimeException

## [1.7.1] - 2024-3-28

### Changed

* 重命名 shutdownNativeThreads 为 shutdownThreads
* 修改 InternalException 构造函数

## [1.7.0] - 2024-3-28

### Changed

* 移除 NetworkFuture，改回 CompleteFuture
* 移动全局方法到 Main 中

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

将 Version 中的 CompletableFuture 改为 NetworkFuture

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
