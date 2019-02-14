package com.zkkc.track.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ShiJunRan on 2019/2/12
 * 数据库表
 */
@Entity
public class PicGreenDaoBean {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 1383977604)
    public PicGreenDaoBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 138575530)
    public PicGreenDaoBean() {
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

}
