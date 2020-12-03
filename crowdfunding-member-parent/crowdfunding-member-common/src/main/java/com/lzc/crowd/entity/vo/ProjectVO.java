package com.lzc.crowd.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
public class ProjectVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 分类 id 集合
     */
    private List<Integer> typeIdList;
    /**
     * 标签 id 集合
     */
    private List<Integer> tagIdList;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目描述
     */
    private String projectDescription;
    /**
     * 计划筹集的金额
     */
    private Integer money;
    /**
     * 筹集资金的天数
     */
    private Integer day;
    /**
     * 创建项目的日期
     */
    private String createDate;
    /**
     * 头图的路径
     */
    private String headerPicturePath;
    /**
     * 详情图片的路径
     */
    private List<String> detailPicturePathList;
    /**
     * 发起人信息
     */
    private MemberLaunchInfoVO memberLaunchInfoVO;
    /**
     *  回报信息集合
     */
    private List<ReturnVO> returnVOList;
    /**
     * 发起人确认信息
     */
    private MemberConfirmInfoVO memberConfirmInfoVO;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Integer> getTypeIdList() {
        return typeIdList;
    }

    public void setTypeIdList(List<Integer> typeIdList) {
        this.typeIdList = typeIdList;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath;
    }

    public List<String> getDetailPicturePathList() {
        return detailPicturePathList;
    }

    public void setDetailPicturePathList(List<String> detailPicturePathList) {
        this.detailPicturePathList = detailPicturePathList;
    }

    public MemberLaunchInfoVO getMemberLaunchInfoVO() {
        return memberLaunchInfoVO;
    }

    public void setMemberLaunchInfoVO(MemberLaunchInfoVO memberLaunchInfoVO) {
        this.memberLaunchInfoVO = memberLaunchInfoVO;
    }

    public List<ReturnVO> getReturnVOList() {
        return returnVOList;
    }

    public void setReturnVOList(List<ReturnVO> returnVOList) {
        this.returnVOList = returnVOList;
    }

    public MemberConfirmInfoVO getMemberConfirmInfoVO() {
        return memberConfirmInfoVO;
    }

    public void setMemberConfirmInfoVO(MemberConfirmInfoVO memberConfirmInfoVO) {
        this.memberConfirmInfoVO = memberConfirmInfoVO;
    }

    @Override
    public String toString() {
        return "ProjectVO{" +
                "typeIdList=" + typeIdList +
                ", tagIdList=" + tagIdList +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", money=" + money +
                ", day=" + day +
                ", createDate='" + createDate + '\'' +
                ", headerPicturePath='" + headerPicturePath + '\'' +
                ", detailPicturePathList=" + detailPicturePathList +
                ", memberLaunchInfoVO=" + memberLaunchInfoVO +
                ", returnVOList=" + returnVOList +
                ", memberConfirmInfoVO=" + memberConfirmInfoVO +
                '}';
    }
}

