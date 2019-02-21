package com.zkkc.track.moudle.pic.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.luoxudong.app.threadpool.ThreadPoolHelp;
import com.zkkc.track.R;
import com.zkkc.track.base.BaseActivity;
import com.zkkc.track.entity.PicGreenDaoBean;
import com.zkkc.track.moudle.pic.adapter.AdPic;
import com.zkkc.track.moudle.pic.contract.PictureContract;
import com.zkkc.track.moudle.pic.entity.PicBean;
import com.zkkc.track.moudle.pic.presenter.PicturePresenter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ShiJunRan on 2019/1/23
 * 图片查看页面
 */
public class PictureAct extends BaseActivity<PictureContract.View, PictureContract.Presenter> implements PictureContract.View {

    @BindView(R.id.pvShow)
    PhotoView pvShow;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.tvMemory)
    TextView tvMemory;
    @BindView(R.id.rvPicture)
    RecyclerView rvPicture;
    @BindView(R.id.tvNoPic)
    TextView tvNoPic;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnDel)
    Button btnDel;
    @BindView(R.id.llMake)
    LinearLayout llMake;
    @BindView(R.id.btnAll)
    Button btnAll;
    @BindView(R.id.searchView)
    SearchView searchView;
    private ExecutorService threadPool;


    private List<PicBean> mList = new ArrayList<PicBean>();
    private List<PicBean> removeList = new ArrayList<PicBean>();
    private AdPic adPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fullscreen(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.picture_act;
    }

    @Override
    public PictureContract.Presenter createPresenter() {
        return new PicturePresenter(this);
    }


    @Override
    public PictureContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        threadPool = ThreadPoolHelp.Builder
                .cached()
                .builder();
        rvPicture.setLayoutManager(new GridLayoutManager(this, 3));
        adPic = new AdPic(R.layout.item_pic_show, mList);
        rvPicture.setAdapter(adPic);
        adPic.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Uri uri = UriUtils.file2Uri(mList.get(position).getmFile());
                pvShow.setImageURI(uri);
            }
        });
        adPic.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                llMake.setVisibility(View.VISIBLE);
                mList = adapter.getData();
                for (PicBean bean : mList) {
                    bean.setShow(true);
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        getPresenter().getPicData(threadPool);
        //获取内存情况
        String internalMemorySize = getInternalMemorySize(getApplicationContext());
        String availableInternalMemorySize = getAvailableInternalMemorySize(getApplicationContext());
        tvMemory.setText(availableInternalMemorySize + "/" + internalMemorySize);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                getPresenter().queryPic(threadPool, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * 获取手机内部存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public static String getInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long size = blockCountLong * blockSizeLong;
        return Formatter.formatFileSize(context, size);
    }

    /**
     * 获取手机内部可用存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public static String getAvailableInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        return Formatter.formatFileSize(context, availableBlocksLong
                * blockSizeLong);
    }

    PicBean picBean;
//    List<PicBean> picCopyBeans;
    @Override
    public void getPicSucceed(List<File> files) {
        if (files.size() > 0) {
            tvNoPic.setVisibility(View.GONE);
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(files.size() - i - 1);
                String fileName = FileUtils.getFileNameNoExtension(file);
                picBean = new PicBean(file, fileName, false, false);
                mList.add(picBean);
                LogUtils.v(fileName);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    picCopyBeans = deepCopy(mList);
                    adPic.notifyDataSetChanged();
                }
            });
        } else {
            tvNoPic.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void getPicFailure(String eStr) {
        ToastUtils.showShort(eStr);
    }

    @Override
    public void deletePicOk() {

        removeList.clear();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adPic.notifyDataSetChanged();
                isAllState = false;
                if (mList.size() == 0) {
                    tvNoPic.setVisibility(View.VISIBLE);
                }
                ToastUtils.showShort("删除成功");
            }
        });

    }


    private List<PicBean> mQueryList;
    @Override
    public void queryPicSucceed(List<PicGreenDaoBean> picDaoBeans) {
       mQueryList = new ArrayList<PicBean>();
        for (PicGreenDaoBean picDaoBean : picDaoBeans) {
            for (PicBean bean : mList) {
                if (picDaoBean.getName().equals(bean.getName())) {
                    mQueryList.add(bean);
                }
            }
        }
        Collections.reverse(mQueryList);
        LogUtils.vTag("SJR",mQueryList.size()+"");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adPic.setNewData(mQueryList);
            }
        });

    }

    @Override
    public void queryPicFailure() {
        ToastUtils.showShort("筛选出错");
    }

    boolean isAllState = false;

    @OnClick({R.id.llBack, R.id.btnCancel, R.id.btnAll, R.id.btnDel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llBack:
                finish();
                break;
            case R.id.btnCancel:
                llMake.setVisibility(View.GONE);
                for (PicBean bean : mList) {
                    bean.setShow(false);
                    bean.setEd(false);
                }
                adPic.notifyDataSetChanged();
                isAllState = false;
                break;
            case R.id.btnAll:
                mList = adPic.getData();
                if (isAllState) {
                    for (PicBean bean : mList) {
                        bean.setShow(true);
                        bean.setEd(false);
                    }
                    isAllState = false;
                } else {
                    for (PicBean bean : mList) {
                        bean.setShow(true);
                        bean.setEd(true);
                    }
                    isAllState = true;
                }

                adPic.notifyDataSetChanged();
                break;

            case R.id.btnDel:
                mList = adPic.getData();
                for (PicBean bean : mList) {
                    if (bean.isEd()) {
                        removeList.add(bean);
                    }
                }
                for (PicBean bean : removeList) {
                    mList.remove(bean);
                }
                for (PicBean bean : mList) {
                    bean.setShow(false);
                    bean.setEd(false);
                }
                if (removeList.size() == 0) {
                    ToastUtils.showShort("请选择要删除的图片");
                } else {
                    llMake.setVisibility(View.GONE);
                    getPresenter().deletePic(threadPool, removeList);
                }


                break;
        }
    }

    public static <E> List<E> deepCopy(List<E> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<E> dest = (List<E>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<E>();
        }
    }
}
