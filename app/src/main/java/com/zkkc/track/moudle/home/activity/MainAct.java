package com.zkkc.track.moudle.home.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kongqw.rockerlibrary.view.RockerView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;
import com.zkkc.track.Constant;
import com.zkkc.track.R;
import com.zkkc.track.base.BaseActivity;
import com.zkkc.track.entity.BatteryStateBean;
import com.zkkc.track.entity.HostDaoBean;
import com.zkkc.track.moudle.config.activity.ConfigAct;
import com.zkkc.track.moudle.home.contract.MainContract;
import com.zkkc.track.moudle.home.entity.SendData;
import com.zkkc.track.moudle.home.presenter.MainPresenter;
import com.zkkc.track.moudle.home.utils.HexResultUtils;
import com.zkkc.track.moudle.pic.activity.PictureAct;
import com.zkkc.track.receiver.BatteryChangedReceiver;
import com.zkkc.track.widget.EmptyControlVideo;
import com.zkkc.track.widget.HorTextClock;
import com.zkkc.track.widget.seekbar.VerticalSeekBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainAct extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View {
    //顶部布局按钮
    //配置
    @BindView(R.id.llDeploy)
    LinearLayout llDeploy;
    //自动对焦
    @BindView(R.id.llAutoF)
    LinearLayout llAutoF;
    //LED灯
    @BindView(R.id.ivLedS)
    ImageView ivLedS;
    @BindView(R.id.tvLedS)
    TextView tvLedS;
    @BindView(R.id.llLedS)
    LinearLayout llLedS;
    //照片
    @BindView(R.id.llPhoto)
    LinearLayout llPhoto;
    //机器倾斜度
    @BindView(R.id.ivBalanced)
    ImageView ivBalanced;
    @BindView(R.id.tvBalanced)
    TextView tvBalanced;
    //摆臂角度
    @BindView(R.id.ivAngle)
    ImageView ivAngle;
    @BindView(R.id.tvAngle)
    TextView tvAngle;
    //信号强度
    @BindView(R.id.ivRS)
    ImageView ivRS;
    @BindView(R.id.tvRS)
    TextView tvRS;
    //连接状态
    @BindView(R.id.ivConnect)
    ImageView ivConnect;
    @BindView(R.id.tvConnect)
    TextView tvConnect;
    @BindView(R.id.llConnect)
    LinearLayout llConnect;
    //手持设备电量
    @BindView(R.id.tvPowNumMy)
    TextView tvPowNumMy;
    @BindView(R.id.ivChongDian)
    ImageView ivChongDian;
    @BindView(R.id.ivPowBg)
    ImageView ivPowBg;
    //机器设备电量
    @BindView(R.id.ivEngineYou)
    ImageView ivEngineYou;
    @BindView(R.id.tvPowNumYou)
    TextView tvPowNumYou;
    @BindView(R.id.tvPowBotYou)
    TextView tvPowBotYou;
    //关闭
    @BindView(R.id.llClose)
    LinearLayout llClose;
    //预警和提示
    @BindView(R.id.tvSos)
    TextView tvSos;
    @BindView(R.id.tvNoVideo)
    TextView tvNoVideo;
    //时间刻印
    @BindView(R.id.tvDateShow)
    HorTextClock tvDateShow;

    //摆臂按钮
    @BindView(R.id.ibUp)
    ImageButton ibUp;
    @BindView(R.id.ibDown)
    ImageButton ibDown;
    //摆臂调档
    @BindView(R.id.ibLeft)
    ImageButton ibLeft;
    @BindView(R.id.tvGradeNum)
    TextView tvGradeNum;
    @BindView(R.id.ibRight)
    ImageButton ibRight;

    //行进调档
    @BindView(R.id.ibXJD)
    ImageButton ibXJD;
    @BindView(R.id.tvSuDu)
    TextView tvSuDu;
    @BindView(R.id.ibXJD2)
    ImageButton ibXJD2;
    @BindView(R.id.tvSuDu2)
    TextView tvSuDu2;
    @BindView(R.id.ibXJD3)
    ImageButton ibXJD3;
    @BindView(R.id.tvSuDu3)
    TextView tvSuDu3;
    //抓拍
    @BindView(R.id.ibPhotograph)
    ImageButton ibPhotograph;
    //LED亮度seekBar
    @BindView(R.id.ledPro)
    VerticalSeekBar ledPro;
    //调焦
    @BindView(R.id.ibAdd)
    ImageButton ibAdd;
    @BindView(R.id.ibSubtract)
    ImageButton ibSubtract;
    //左右操作盘
    @BindView(R.id.rockerViewLeft)
    RockerView rockerViewLeft;
    @BindView(R.id.rockerViewRight)
    RockerView rockerViewRight;
    //播放器
    @BindView(R.id.detail_player)
    EmptyControlVideo detailPlayer;
    //操作显示（隐藏）
    @BindView(R.id.btnHandle)
    ImageView btnHandle;
    //重新加载视频
    @BindView(R.id.btnRePlay)
    ImageView btnRePlay;


    //电池广播接收数据
    private static final String BATTERY_STATUS_CHARGING = "BATTERY_STATUS_CHARGING";
    private static final String BATTERY_STATUS_FULL = "BATTERY_STATUS_FULL";
    private static final String BATTERY_STATUS_NOT_CHARGING = "BATTERY_STATUS_NOT_CHARGING";


    private BatteryChangedReceiver batteryChangedReceiver;
    private String batteryType;//充电状态
    private int powNum;//当前电量
    private int gear = 5;//默认摆臂档数为5档 最高9档
    private ExecutorService threadPool;


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void batteryEvent(BatteryStateBean stateBean) {
        batteryType = stateBean.batteryType;
        powNum = stateBean.powStr;
        tvPowNumMy.setText(powNum + "%");
        if (powNum < 10) {
            ivPowBg.setImageResource(R.mipmap.dianliang_a);
        } else if (powNum >= 10 && powNum < 20) {
            ivPowBg.setImageResource(R.mipmap.dianliang_b);
        } else if (powNum >= 20 && powNum < 40) {
            ivPowBg.setImageResource(R.mipmap.dianliang_c);
        } else if (powNum >= 40 && powNum < 90) {
            ivPowBg.setImageResource(R.mipmap.dianliang_d);
        } else if (powNum >= 90) {
            ivPowBg.setImageResource(R.mipmap.dianliang_e);
        }
        switch (batteryType) {
            case BATTERY_STATUS_CHARGING://充电中
                ivChongDian.setVisibility(View.VISIBLE);
                break;
            case BATTERY_STATUS_FULL://已充满
                ivChongDian.setVisibility(View.VISIBLE);
                break;
            case BATTERY_STATUS_NOT_CHARGING://停止充电
                ivChongDian.setVisibility(View.GONE);
                break;

        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.main_act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen(true);
        EventBus.getDefault().register(this);
        HexResultUtils.hideBottomUIMenu(this);
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public MainContract.View createView() {
        return this;
    }


    @Override
    public void init() {
        //动态权限
        permissionsSet();
        //设置seekBar的样式和大小相关
        ledPro.setSelectColor(getResources().getColor(R.color.yellow));
        ledPro.setThumb(R.mipmap.bg_seekbar);
        ledPro.setThumbSize(35, 25);
        ledPro.setProgress(0);
        setOnClicked();//LED灯seekBar亮度监听
        //电量广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        batteryChangedReceiver = new BatteryChangedReceiver();
        registerReceiver(batteryChangedReceiver, filter);
        //遥杆监听
        initRocker();
        threadPool = ThreadPoolHelp.Builder
                .cached()
                .builder();
        //播放器配置初始化
        initRTSPVideo();

        ibUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtils.v("ACTION_DOWN");
                        if (socketState) {
                            sendData = new SendData(HexResultUtils.sendBBUp());
                            manager.send(sendData);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtils.v("ACTION_UP");
                        if (socketState) {
                            sendData = new SendData(HexResultUtils.sendBBStop());
                            manager.send(sendData);
                        }
                        break;
                }
                return false;
            }
        });
        ibDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtils.v("ACTION_DOWN");
                        if (socketState) {
                            sendData = new SendData(HexResultUtils.sendBBDown());
                            manager.send(sendData);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtils.v("ACTION_UP");
                        if (socketState) {
                            sendData = new SendData(HexResultUtils.sendBBStop());
                            manager.send(sendData);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void initRTSPVideo() {
        VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp");
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "allowed_media_types", "video"); //根据媒体类型来配置
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 5000);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "buffer_size", 1316);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1);  // 无限读
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 200);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1);
        list.add(videoOptionModel);
        //  关闭播放器缓冲，这个必须关闭，否则会出现播放一段时间后，一直卡主，控制台打印 FFP_MSG_BUFFERING_START
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
        list.add(videoOptionModel);


        // 视频帧处理不过来的时候丢弃一些帧达到同步的效果
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 0);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "fps", 30);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
        list.add(videoOptionModel);

        GSYVideoManager.instance().setOptionModelList(list);
        detailPlayer.setVideoAllCallBack(new GSYSampleCallBack() {

            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                LogUtils.eTag("SJR", "onPrepared-->" + url);
                detailPlayer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
                LogUtils.eTag("SJR", "onAutoComplete");
                //停止Service后回调


            }

            @Override
            public void onPlayError(String url, Object... objects) {
                super.onPlayError(url, objects);
                LogUtils.eTag("SJR", "onPlayError");
                //服务端未开启

            }
        });

    }


    private boolean isSpread = false;
    private boolean isRockerViewHide = true;

    @OnClick({R.id.ibXJD, R.id.tvSuDu, R.id.ibXJD2, R.id.tvSuDu2, R.id.ibXJD3, R.id.tvSuDu3,
            R.id.llDeploy, R.id.llAutoF, R.id.llLedS, R.id.llPhoto, R.id.llConnect, R.id.llClose,
            R.id.ibPhotograph, R.id.btnHandle, R.id.btnRePlay})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            //行进调档
            case R.id.ibXJD:
            case R.id.tvSuDu:
                if (isSpread) {
                    reSetButBg(0);
                    tvSuDu.setText("低速");
                    tvSuDu.setTextColor(getResources().getColor(R.color.green));
                    isSpread = false;
                    //TODO 行进选档
                    if (socketState) {
                        sendData = new SendData(HexResultUtils.sendGoSpeed(1));
                        manager.send(sendData);
                    }
                    ToastUtils.showShort("低速");


                } else {
                    reSetButBg(1);
                    tvSuDu.setText("低速");
                    tvSuDu.setTextColor(getResources().getColor(R.color.green));
                    isSpread = true;
                }
                break;
            case R.id.ibXJD2:
            case R.id.tvSuDu2:
                if (isSpread) {
                    reSetButBg(0);
                    tvSuDu.setText("中速");
                    tvSuDu.setTextColor(getResources().getColor(R.color.violet));
                    isSpread = false;
                    //TODO 行进选档
                    if (socketState) {
                        sendData = new SendData(HexResultUtils.sendGoSpeed(2));
                        manager.send(sendData);
                    }
                    ToastUtils.showShort("中速");
                }
                break;
            case R.id.ibXJD3:
            case R.id.tvSuDu3:
                if (isSpread) {
                    reSetButBg(0);
                    tvSuDu.setText("高速");
                    tvSuDu.setTextColor(getResources().getColor(R.color.colorAccent));
                    isSpread = false;
                    //TODO 行进选档
                    if (socketState) {
                        sendData = new SendData(HexResultUtils.sendGoSpeed(3));
                        manager.send(sendData);
                    }
                    ToastUtils.showShort("高速");
                }

                break;

            //顶部tab
            case R.id.llDeploy://配置
//                msg8[0] = 0x01;
//                sendAndGetSocketData(msg8);
                startActivity(new Intent(MainAct.this, ConfigAct.class));
                break;
            case R.id.llAutoF://自动对焦
//                byte[] s = HexResultUtils.sendZJUp();
//                LogUtils.v("CRC16--->"+ HexUtil.byte2HexStr(s));

                break;
            case R.id.llLedS://LED灯
                int currentState = detailPlayer.getCurrentState();
                ToastUtils.showShort("" + currentState);
                break;
            case R.id.llPhoto://照片
                startActivity(new Intent(MainAct.this, PictureAct.class));

                break;
            case R.id.llConnect://连接
                showConnectDialog();
                break;
            case R.id.llClose://关闭
                showCloseDialog();
                break;
            case R.id.ibPhotograph://抓拍
                boolean inPlayingState = detailPlayer.isInPlayingState();
                if (inPlayingState) {
                    getPresenter().photoGraph(this, detailPlayer, threadPool);
                } else {
                    ToastUtils.showShort("请连接主机摄像头");
                }


                break;
            case R.id.btnHandle://操作盘控制（显示，隐藏)
                if (isRockerViewHide) {
                    btnHandle.setBackgroundResource(R.mipmap.ic_handle_b);
                    rockerViewLeft.setVisibility(View.VISIBLE);
                    rockerViewRight.setVisibility(View.VISIBLE);
                    isRockerViewHide = false;
                } else {
                    btnHandle.setBackgroundResource(R.mipmap.ic_handle_a);
                    rockerViewLeft.setVisibility(View.GONE);
                    rockerViewRight.setVisibility(View.GONE);
                    isRockerViewHide = true;
                }


                break;
            case R.id.btnRePlay://重新加载视频

                if (socketState) {
                    detailPlayer.setUp(HexResultUtils.toPlayUrl(hostDaobean, Constant.IS_DEBUG_URL), false, "");
                    detailPlayer.startPlayLogic();//视频播放
                }
                break;

        }
    }

    /**
     * 连接Dialog
     */
    Dialog connectDialog;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private boolean socketState = false;
    private byte[] msg8 = new byte[8];
    private byte[] msg9 = new byte[9];
    TextView tvNameDia;
    TextView tvhIpDia;
    TextView tvhPortDia;
    TextView tvuNameDia;
    TextView tvuPw;
    Button btnConnect;
    boolean isSetHost = false;//是否设置了主机IP

    HostDaoBean hostDaobean;

    private void showConnectDialog() {
        View diaView = View.inflate(this, R.layout.connect_dialog, null);
        connectDialog = new Dialog(this);
        connectDialog.setContentView(diaView);
        tvNameDia = diaView.findViewById(R.id.tvNameDia);
        tvhIpDia = diaView.findViewById(R.id.tvhIpDia);
        tvhPortDia = diaView.findViewById(R.id.tvhPortDia);
        tvuNameDia = diaView.findViewById(R.id.tvuNameDia);
        tvuPw = diaView.findViewById(R.id.tvuPw);
        btnConnect = diaView.findViewById(R.id.btnConnect);
        connectDialog.show();
        getPresenter().switchoverHost(threadPool);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = HexResultUtils.toPlayUrl(hostDaobean, Constant.IS_DEBUG_URL);
                LogUtils.v(s);
                if (isSetHost) {
                    if (socketState) {
                        manager.disconnect();//主动断开socket
                    } else {
                        String mIp = hostDaobean.getHIp();
                        String strPort = hostDaobean.getHPort();
                        if (!mIp.equals("") && !strPort.equals("")) {
                            int mPort = Integer.parseInt(strPort);
                            socketRun(mIp, mPort);
                        }
                    }

                } else {
                    connectDialog.dismiss();
                    startActivity(new Intent(MainAct.this, ConfigAct.class));
                }

            }
        });

    }

    ConnectionInfo info;
    IConnectionManager manager;

    private void socketRun(String mIp, int mPort) {
        //连接参数设置(IP,端口号),这也是一个连接的唯一标识,不同连接,该参数中的两个值至少有其一不一样
        info = new ConnectionInfo(mIp, mPort);
        //调用OkSocket,开启这次连接的通道,调用通道的连接方法进行连接.
        manager = OkSocket.open(info);
        //获得当前连接通道的参配对象
        OkSocketOptions mOptions = manager.getOption();
        OkSocketOptions.Builder optionsBuilder = new OkSocketOptions.Builder(mOptions);
        optionsBuilder.setReconnectionManager(new NoneReconnect());
        optionsBuilder.setConnectTimeoutSecond(2);
        optionsBuilder.setReaderProtocol(new IReaderProtocol() {
            @Override
            public int getHeaderLength() {

                return 1;
            }

            @Override
            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                return 12;
            }
        });
        manager.option(optionsBuilder.build());
        manager.connect();
        manager.registerReceiver(new SocketActionAdapter() {
            @Override
            public void onSocketIOThreadShutdown(String action, Exception e) {//主动中断连接
                super.onSocketIOThreadShutdown(action, e);
                connectDialog.dismiss();
                ToastUtils.showShort("主机连接断开");
                socketState = false;
                detailPlayer.onVideoPause();
                detailPlayer.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {//服务器连接中断
                super.onSocketDisconnection(info, action, e);
                LogUtils.v("onSocketDisconnection---" + e.toString());
                ToastUtils.showShort("主机连接断开");
                socketState = false;
                detailPlayer.onVideoPause();
                detailPlayer.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
                super.onSocketConnectionSuccess(info, action);
                if (connectDialog != null) {
                    connectDialog.dismiss();
                    ToastUtils.showShort("连接成功");
                    socketState = true;
                    //查询行进档位
                    //TODO----------查询档位(行进档位和摆臂档位)，----暂时
                    if (socketState) {
                        sendData = new SendData(HexResultUtils.sendGoQuery());
                        manager.send(sendData);
                        sendData = new SendData(HexResultUtils.sendBBSpeed(3));
                        manager.send(sendData);
                    }


                    //播放视频
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (socketState) {

                                detailPlayer.setUp(HexResultUtils.toPlayUrl(hostDaobean, Constant.IS_DEBUG_URL), false, "");
                                detailPlayer.startPlayLogic();//视频播放
                            }
                        }
                    });

                }
            }

            @Override
            public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
                super.onSocketConnectionFailed(info, action, e);
                LogUtils.v("onSocketConnectionFailed---" + e.toString());
                ToastUtils.showShort("主机未开启");
                socketState = false;
            }

            @Override
            public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
                super.onSocketReadResponse(info, action, data);
                String s = ConvertUtils.bytes2HexString(data.getHeadBytes());
                String str = ConvertUtils.bytes2HexString(data.getBodyBytes());
                LogUtils.v("onSocketReadResponse---" + s + "----" + str);
            }

            @Override
            public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
                super.onSocketWriteResponse(info, action, data);
                String str = ConvertUtils.bytes2HexString(data.parse());
                LogUtils.v("onSocketWriteResponse--" + str);

            }
        });
    }


    /**
     * 档位选择按钮变化
     *
     * @param num
     */
    private void reSetButBg(int num) {
        switch (num) {
            case 0://收缩状态
                ibXJD.setVisibility(View.VISIBLE);
                tvSuDu.setVisibility(View.VISIBLE);
                ibXJD2.setVisibility(View.GONE);
                tvSuDu2.setVisibility(View.GONE);
                ibXJD3.setVisibility(View.GONE);
                tvSuDu3.setVisibility(View.GONE);
                break;
            case 1://展开状态
                ibXJD.setVisibility(View.VISIBLE);
                tvSuDu.setVisibility(View.VISIBLE);
                ibXJD2.setVisibility(View.VISIBLE);
                tvSuDu2.setVisibility(View.VISIBLE);
                ibXJD3.setVisibility(View.VISIBLE);
                tvSuDu3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private SendData sendData;

    /**
     * 遥杆监听
     */
    private void initRocker() {
        //有状态变化的时候回调
        rockerViewLeft.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        rockerViewLeft.setOnShakeListener(RockerView.DirectionMode.DIRECTION_8, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {
                Log.d("SJR", "onStart");
            }

            @Override
            public void direction(RockerView.Direction direction) {
                Log.d("SJR", "direction: " + direction);
                if (!socketState) {
                    return;
                }
                switch (direction) {
                    case DIRECTION_UP:
                        sendData = new SendData(HexResultUtils.sendZJUp());
                        manager.send(sendData);
                        break;
                    case DIRECTION_UP_RIGHT:
                        sendData = new SendData(HexResultUtils.sendZJUpAndRight());
                        manager.send(sendData);
                        break;
                    case DIRECTION_RIGHT:
                        sendData = new SendData(HexResultUtils.sendZJRight());
                        manager.send(sendData);
                        break;
                    case DIRECTION_DOWN_RIGHT:
                        sendData = new SendData(HexResultUtils.sendZJDownAndRight());
                        manager.send(sendData);
                        break;
                    case DIRECTION_DOWN:
                        sendData = new SendData(HexResultUtils.sendZJDown());
                        manager.send(sendData);
                        break;
                    case DIRECTION_DOWN_LEFT:
                        sendData = new SendData(HexResultUtils.sendZJDownAndLeft());
                        manager.send(sendData);
                        break;
                    case DIRECTION_LEFT:
                        sendData = new SendData(HexResultUtils.sendZJLeft());
                        manager.send(sendData);
                        break;
                    case DIRECTION_UP_LEFT:
                        sendData = new SendData(HexResultUtils.sendZJUpAndLeft());
                        manager.send(sendData);
                        break;
                }
            }

            @Override
            public void onFinish() {
                Log.d("SJR", "onFinish");
                if (!socketState) {
                    return;
                }
                sendData = new SendData(HexResultUtils.sendZJStopMove());
                manager.send(sendData);
            }
        });

        rockerViewRight.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        rockerViewRight.setOnShakeListener(RockerView.DirectionMode.DIRECTION_4_ROTATE_45, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {
                Log.d("SJR", "onStart");
            }

            @Override
            public void direction(RockerView.Direction direction) {
                Log.d("SJR", "direction: " + direction);
            }

            @Override
            public void onFinish() {
                Log.d("SJR", "onFinish");
            }
        });


    }

    /**
     * LED灯seekBar亮度监听
     */
    private void setOnClicked() {
        ledPro.setOnSlideChangeListener(new VerticalSeekBar.SlideChangeListener() {
            @Override
            public void onStart(VerticalSeekBar slideView, int progress) {

            }

            @Override
            public void onProgress(VerticalSeekBar slideView, int progress) {

            }

            @Override
            public void onStop(VerticalSeekBar slideView, int progress) {
                //TODO  LED 亮度调节
            }
        });
    }

    /**
     * 动态权限申请
     */
    private void permissionsSet() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            // All requested permissions are granted
                        } else {
                            // At least one permission is denied
//                            permissionsSet();
                        }
                    }
                });
    }

    /**
     * 关闭退出Dialog
     */
    Dialog closeDialog;

    private void showCloseDialog() {
        View dialogView = View.inflate(this, R.layout.close_dialog, null);
        closeDialog = new Dialog(this);
        closeDialog.setContentView(dialogView);
        closeDialog.show();
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog.dismiss();
                AppExit();
            }
        });

    }

    /**
     * 摆臂调档点击
     */
    @OnClick({R.id.ibLeft, R.id.ibRight})
    public void onIbClicked(View view) {
        switch (view.getId()) {
            case R.id.ibLeft:

                if (gear > 1) {
                    gear--;
                    //TODO 摆臂调档 -
                    if (socketState) {
                        sendData = new SendData(HexResultUtils.sendBBSpeed(2));
                        manager.send(sendData);
                    }
                } else {
                    ToastUtils.showShort("当前已是最低档！");
                }

                break;
            case R.id.ibRight:

                if (gear < 9) {
                    gear++;
                    //TODO 摆臂调档 +
                    if (socketState) {
                        sendData = new SendData(HexResultUtils.sendBBSpeed(1));
                        manager.send(sendData);
                    }
                } else {
                    ToastUtils.showShort("当前已是最高档！");
                }
                break;


        }
        tvGradeNum.setText(gear + "");
    }


    @Override
    protected void onResume() {
        super.onResume();
//        detailPlayer.onVideoResume();
//        if (socketState) {
//            detailPlayer.setUp(HexResultUtils.toPlayUrl(hostDaobean,Constant.IS_DEBUG_URL), false, "");
//            detailPlayer.startPlayLogic();//视频播放
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        detailPlayer.onVideoPause();
    }


    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryChangedReceiver);
        EventBus.getDefault().unregister(this);
        if (connectDialog != null) {
            connectDialog.dismiss();
            connectDialog = null;
        }
        if (closeDialog != null) {
            closeDialog.dismiss();
            closeDialog = null;
        }
        GSYVideoManager.releaseAllVideos();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        finish();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            showCloseDialog();
        }
        return true;
    }

    @Override
    public void photoGraphSucceed() {
        ToastUtils.showShort("抓拍成功");
    }

    @Override
    public void photoGraphErr(String err) {
        ToastUtils.showShort(err);
    }

    @Override
    public void switchoverHostOk(HostDaoBean bean) {
        this.hostDaobean = bean;
        isSetHost = true;
        tvNameDia.setText(bean.getName());
        tvhIpDia.setText(bean.getHIp());
        tvhPortDia.setText(bean.getHPort());
        tvuNameDia.setText(bean.getHName());
        tvuPw.setText("********");
        if (socketState) {
            btnConnect.setText("断开连接");
        } else {
            btnConnect.setText("连接主机");
        }


    }

    @Override
    public void switchoverHostErr() {
        hostDaobean = null;
        isSetHost = false;
        tvNameDia.setText("");
        tvhIpDia.setText("");
        tvhPortDia.setText("");
        tvuNameDia.setText("");
        tvuPw.setText("");
        btnConnect.setText("去配置主机");

    }
}
