package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.common.Constants;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 附件
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
@Entity
@Table(name = "trans_file")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32,nullable = false)
    @Field("文件Id")
    private String fileId;

    @Column(length = 36,nullable = false)
    @Field("文件的外部关联Key")
    private String fileKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Field("创建时间")
    private Date createAt;

    @Column(length = 255,nullable = false)
    @Field("名称")
    private String fileName;

    @Column(length = 255)
    @Field("编号")
    private String fileNo;

    @Column(length = 255)
    @Field("作者")
    private String fileAuthor;

    @Column(length = 20)
    @Field("行政区代码")
    private String regionCode;

    @Column
    @Field("大小")
    private long fileSize;

    @Column(length = 500)
    @Field("存储路径")
    private String storePath;

    @Column(length = 500)
    @Field("描述")
    private String description;

    @Column(length = 100,nullable = false)
    @Field("附件类型")
    private String fileType = Constants.FileType.QT.getCode();


    @Column(length = 20)
    @Field("分辨率")
    private String resolution;

    @Transient
    private String url;

    @Transient
    @JsonSubTypes(@JsonSubTypes.Type(value=FileInputStream.class))
    private FileInputStream inputStream;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public FileInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(FileInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileAuthor() {
        return fileAuthor;
    }

    public void setFileAuthor(String fileAuthor) {
        this.fileAuthor = fileAuthor;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
