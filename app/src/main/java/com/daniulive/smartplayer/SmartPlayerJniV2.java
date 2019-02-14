/*
 * SmartPlayerJniV2.java
 * SmartPlayerJniV2
 *
 * WebSite: http://daniulive.com
 * Github: https://github.com/daniulive/SmarterStreaming
 * 
 * Created by DaniuLive on 2015/09/26.
 * Copyright © 2014~2018 DaniuLive. All rights reserved.
 */

package com.daniulive.smartplayer;


import com.eventhandle.NTSmartEventCallbackV2;

public class SmartPlayerJniV2 {
	/**
	 * Initialize Player.
	 *
	 * @param ctx: get by this.getApplicationContext()
	 *
	 * <pre>This function must be called firstly.</pre>
	 *
	 * @return player handle if successful, if return 0, which means init failed. 
	 */

	public native long SmartPlayerOpen(Object ctx);

	/**
	 * Set callbackv2 event
	 *
	 * @param callbackv2 function
	 *
	 * @return {0} if successful
	 */
	public native int SetSmartPlayerEventCallbackV2(long handle, NTSmartEventCallbackV2 callbackv2);

	/**
	 * Set Video H.264 HW decoder, if support H.264 HW decoder, it will return 0
	 *
	 * @param isHWDecoder: 0: software decoder; 1: hardware decoder.
	 *
	 * @return {0} if successful
	 */
	public native int SetSmartPlayerVideoHWDecoder(long handle, int isHWDecoder);

	/**
	 * Set Video H.265(hevc) HW decoder, if support H.265(hevc) HW decoder, it will return 0
	 *
	 * @param isHevcHWDecoder: 0: software decoder; 1: hardware decoder.
	 *
	 * @return {0} if successful
	 */
	public native int SetSmartPlayerVideoHevcHWDecoder(long handle, int isHevcHWDecoder);

	/**
	 * Set Surface view.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param surface: surface view
	 *
	 * <pre> NOTE: if not set or set surface with null, it will playback audio only. </pre> 
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetSurface(long handle, Object surface);

	/**
	 * 设置视频硬解码下Mediacodec自行绘制模式（此种模式下，硬解码兼容性和效率更好，回调YUV/RGB和快照功能将不可用）
	 *
	 * @param isHWRenderMode: 0: not enable; 1: 用SmartPlayerSetSurface设置的surface自行绘制
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetHWRenderMode(long handle, int isHWRenderMode);

	/**
	 * 更新硬解码surface
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerUpdateHWRenderSurface(long handle);

	/**
	 * Set External Render.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param external_render:  External Render
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetExternalRender(long handle, Object external_render);

	/**
	 * Set External Audio Output.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param external_audio_output:  External Audio Output
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetExternalAudioOutput(long handle, Object external_audio_output);

	/**
	 * Set Audio Data Callback.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param audio_data_callback: Audio Data Callback.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetAudioDataCallback(long handle, Object audio_data_callback);

	/**
	 * Set Video Data Callback.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param video_data_callback: Video Data Callback.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetVideoDataCallback(long handle, Object video_data_callback);

	/**
	 * Set user data Callback.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param : user_data_callback Callback.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetUserDataCallback(long handle, Object user_data_callback);

	/**
	 * Set SEI data Callback.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param : sei_data_callback Callback.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetSEIDataCallback(long handle, Object sei_data_callback);

	/**
	 * Set AudioOutput Type
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param use_audiotrack:
	 *
	 * <pre> NOTE: if use_audiotrack with 0: it will use auto-select output devices; if with 1: will use audiotrack mode. </pre>
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetAudioOutputType(long handle, int use_audiotrack);


	/**
	 * Set buffer
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param buffer:
	 *
	 * <pre> NOTE: Unit is millisecond, range is 0-5000 ms </pre>
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetBuffer(long handle, int buffer);


	/**
	 * Set mute or not
	 *
	 * @param is_mute: if with 1:mute, if with 0: does not mute
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetMute(long handle, int is_mute);


	/**
	 * It's only used when playback RTSP stream
	 *
	 * Default with UDP mode
	 *
	 * @param is_using_tcp: if with 1, it will via TCP mode, while 0 with UDP mode
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRTSPTcpMode(long handle, int is_using_tcp);

	/**
	 * 设置RTSP超时时间, timeout单位为秒，必须大于0
	 *
	 * @param timeout: RTSP timeout setting
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRTSPTimeout(long handle, int timeout);

	/**
	 * 设置RTSP TCP/UDP自动切换
	 *
	 * NOTE: 对于RTSP来说，有些可能支持rtp over udp方式，有些可能支持使用rtp over tcp方式.
	 * 为了方便使用，有些场景下可以开启自动尝试切换开关, 打开后如果udp无法播放，sdk会自动尝试tcp, 如果tcp方式播放不了,sdk会自动尝试udp.
	 *
	 * @param is_auto_switch_tcp_udp 如果设置1的话, sdk将在tcp和udp之间尝试切换播放，如果设置为0，则不尝试切换.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRTSPAutoSwitchTcpUdp(long handle, int is_auto_switch_tcp_udp);

	/**
	 * Set fast startup
	 *
	 * @param is_fast_startup: if with 1, it will second play back, if with 0: does not it
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetFastStartup(long handle, int is_fast_startup);



	/**
	 * Set low latency mode
	 *
	 * @param mode: if with 1, low latency mode, if with 0: normal mode
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetLowLatencyMode(long handle, int mode);

	/**
	 * 设置视频垂直反转
	 * @param handle
	 * @param is_flip： 0: 不反转, 1: 反转
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetFlipVertical(long handle, int is_flip);

	/**
	 * 设置视频水平反转
	 * @param handle
	 * @param is_flip： 0: 不反转, 1: 反转
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetFlipHorizontal(long handle, int is_flip);

	/**
	 * 设置顺时针旋转, 注意除了0度之外， 其他角度都会额外消耗性能
	 * @param handle
	 * @param degress： 当前支持 0度，90度, 180度, 270度 旋转
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRotation(long handle, int degress);


	/**
	 * Set report download speed
	 *
	 * @param handle
	 * @param is_report: if with 1, it will report download speed, it with 0: does not it.
	 * @param report_interval: report interval, unit is second, it must be greater than 0.
	 * @return
	 */
	public native int SmartPlayerSetReportDownloadSpeed(long handle, int is_report, int report_interval );

	/**
	 * Set playback orientation.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param surOrg: current orientation,  PORTRAIT 1, LANDSCAPE with 2
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetOrientation(long handle, int surOrg);

	/**
	 * Set if needs to save image during playback stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param is_save_image: if with 1, it will save current image via the interface of SmartPlayerSaveCurImage(), if with 0: does not it
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSaveImageFlag(long handle, int is_save_image);

	/**
	 * Save current image during playback stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param imageName: image name, which including fully path, "/sdcard/daniuliveimage/daniu.png", etc.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSaveCurImage(long handle, String imageName);

	/**
	 * Switch playback url
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param uri: the new playback uri
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSwitchPlaybackUrl(long handle, String uri);

	/**
	 * Create file directory
	 *
	 * @param path,  E.g: /sdcard/daniulive/rec
	 *
	 * <pre> The interface is only used for recording the stream data to local side. </pre>
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerCreateFileDirectory(String path);

	/**
	 * Set recorder directory.
	 *
	 * @param path: the directory of recorder file.
	 *
	 * <pre> NOTE: make sure the path should be existed, or else the setting failed. </pre>
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRecorderDirectory(long handle, String path);

	/**
	 * Set the size of every recorded file.
	 *
	 * @param size: (MB), (5M~500M), if not in this range, set default size with 200MB.
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRecorderFileMaxSize(long handle, int size);

	/*
	 * 设置录像时音频转AAC编码的开关
	 *
	 * aac比较通用，sdk增加其他音频编码(比如speex, pcmu, pcma等)转aac的功能.
	 *
	 * @param is_transcode: 设置为1的话，如果音频编码不是aac，则转成aac，如果是aac，则不做转换. 设置为0的话，则不做任何转换. 默认是0.
	 *
	 * 注意: 转码会增加性能消耗
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetRecorderAudioTranscodeAAC(long handle, int is_transcode);

	/**
	 * Set playback/recorder stream url
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @param uri: playback/recorder uri
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetUrl(long handle, String uri);

	/**
	 * Start playback stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerStartPlay(long handle);

	/**
	 * Stop playback stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerStopPlay(long handle);

	/**
	 * Start recorder stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerStartRecorder(long handle);

	/**
	 * Stop recorder stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerStopRecorder(long handle);

	/*
	 * 设置拉流时音频转AAC编码的开关
	 *
	 * aac比较通用，sdk增加其他音频编码(比如speex, pcmu, pcma等)转aac的功能.
	 *
	 * @param is_transcode: 设置为1的话，如果音频编码不是aac，则转成aac, 如果是aac，则不做转换. 设置为0的话，则不做任何转换. 默认是0.

	 * 注意: 转码会增加性能消耗
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerSetPullStreamAudioTranscodeAAC(long handle, int is_transcode);

	/**
	 * Start pull stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerStartPullStream(long handle);

	/**
	 * Stop pull stream
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerStopPullStream(long handle);

	/**
	 * Close player instance.
	 *
	 * @param handle: return value from SmartPlayerOpen()
	 *
	 * <pre> NOTE: it could not use player handle after call this function. </pre> 
	 *
	 * @return {0} if successful
	 */
	public native int SmartPlayerClose(long handle);
}
