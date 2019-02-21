package com.zkkc.track.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ShiJunRan on 2019/2/21
 */
@Entity
public class HostDaoBean {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String hIp;
    private String hPort;
    private String hName;
    private String hPw;
    private String sIp;
    private String sPort;
    private String mState;//Y 当前选中  N 未选中

    @Generated(hash = 1167199401)
    public HostDaoBean(Long id, String name, String hIp, String hPort, String hName,
                       String hPw, String sIp, String sPort, String mState) {
        this.id = id;
        this.name = name;
        this.hIp = hIp;
        this.hPort = hPort;
        this.hName = hName;
        this.hPw = hPw;
        this.sIp = sIp;
        this.sPort = sPort;
        this.mState = mState;
    }

    @Generated(hash = 675860175)
    public HostDaoBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHIp() {
        return this.hIp;
    }

    public void setHIp(String hIp) {
        this.hIp = hIp;
    }

    public String getHPort() {
        return this.hPort;
    }

    public void setHPort(String hPort) {
        this.hPort = hPort;
    }

    public String getHName() {
        return this.hName;
    }

    public void setHName(String hName) {
        this.hName = hName;
    }

    public String getHPw() {
        return this.hPw;
    }

    public void setHPw(String hPw) {
        this.hPw = hPw;
    }

    public String getSIp() {
        return this.sIp;
    }

    public void setSIp(String sIp) {
        this.sIp = sIp;
    }

    public String getSPort() {
        return this.sPort;
    }

    public void setSPort(String sPort) {
        this.sPort = sPort;
    }

    public String getMState() {
        return this.mState;
    }

    public void setMState(String mState) {
        this.mState = mState;
    }


}
