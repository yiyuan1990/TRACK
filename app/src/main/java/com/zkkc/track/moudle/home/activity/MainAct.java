package com.zkkc.track.moudle.home.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.kongqw.rockerlibrary.view.RockerView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zkkc.track.R;
import com.zkkc.track.base.BaseActivity;
import com.zkkc.track.entity.BatteryStateBean;
import com.zkkc.track.moudle.home.contract.MainContract;
import com.zkkc.track.moudle.home.presenter.MainPresenter;
import com.zkkc.track.moudle.pic.activity.PictureAct;
import com.zkkc.track.receiver.BatteryChangedReceiver;
import com.zkkc.track.utils.SPUtil;
import com.zkkc.track.widget.HorTextClock;
import com.zkkc.track.widget.seekbar.VerticalSeekBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    //拍照
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

    @BindView(R.id.rlBotMatch)
    RelativeLayout rlBotMatch;

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
        ledPro.setThumbSize(30, 20);
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
    }


    private boolean isSpread = false;

    @OnClick({R.id.ibXJD, R.id.tvSuDu, R.id.ibXJD2, R.id.tvSuDu2, R.id.ibXJD3, R.id.tvSuDu3,
            R.id.llDeploy, R.id.llAutoF, R.id.llLedS, R.id.llPhoto, R.id.llConnect, R.id.llClose,
            R.id.ibPhotograph})
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
                }

                break;

            //顶部tab
            case R.id.llDeploy://配置

                break;
            case R.id.llAutoF://自动对焦
                break;
            case R.id.llLedS://LED灯

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
    private String mIp;
    private int mPort;
    private boolean socketState = false;

    private void showConnectDialog() {
        View dialogView = View.inflate(this, R.layout.connect_dialog, null);
        connectDialog = new Dialog(this);
        connectDialog.setContentView(dialogView);
        connectDialog.show();
        final EditText etIp = dialogView.findViewById(R.id.etIp);
        final EditText etPort = dialogView.findViewById(R.id.etPort);
        Button btnConnect = dialogView.findViewById(R.id.btnConnect);
        String strIp = SPUtil.getString(this, "IP", "");
        int inPort = SPUtil.getInt(this, "PORT", 0);
        etIp.setText(strIp);
        if (inPort == 0) {
            etPort.setText("");
        } else {
            etPort.setText(inPort + "");
        }

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击连接的逻辑
                mIp = etIp.getText().toString().trim();
                mPort = Integer.parseInt(etPort.getText().toString().trim());
                boolean ip = SPUtil.putString(MainAct.this, "IP", mIp);
                boolean port = SPUtil.putInt(MainAct.this, "PORT", mPort);
                if (ip && port) {
                    socketRun();
                }


            }
        });
    }

    private void socketRun() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(mIp, mPort), 3000);//连接超时5000毫秒
                    ToastUtils.showShort("连接成功！");
                    if (socket != null) {
                        socketState = true;
                        outputStream = socket.getOutputStream();
                        connectDialog.dismiss();
                    } else {
                        socketState = false;
                    }


                } catch (SocketTimeoutException aa) {
                    ToastUtils.showShort("连接超时！");
                    Log.d("socketRun", aa.getMessage());
                    aa.printStackTrace();
                } catch (IOException e) {
                    ToastUtils.showShort("连接失败--" + e.getMessage());
                    Log.d("socketRun", e.getMessage());
                    e.printStackTrace();
                }

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
            }

            @Override
            public void onFinish() {
                Log.d("SJR", "onFinish");
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
}
