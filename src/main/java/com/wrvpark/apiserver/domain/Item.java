package com.wrvpark.apiserver.domain;

import com.wrvpark.apiserver.dto.ItemDTO;
import com.wrvpark.apiserver.dto.requests.NewItemRequest;
import com.wrvpark.apiserver.util.TimeUtil;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Vahid Haghighat
 */

@Entity
@Table(name = "RVPARK_ITEMS")
public class Item implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_ID", referencedColumnName = "ID", nullable = false)
    private Subcategory subcategory;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "LOTNO")
    private int lotNo;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "MLSLINK")
    private String mlsLink;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "CONSTRUCTORINFO")
    private String constructorInfo;

    @Column(name = "IMAGE", columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User creator;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RENTER_ID", referencedColumnName = "ID", nullable = true)
    private User renter;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ITEMTYPE", nullable = false)
    //columnDefinition = "VARCHAR(100) CHECK (itemtype IN ('Services', 'For Sale or Rent','Lost & Found')")
    private String itemType;

    public Item() {}

    public Item(ItemDTO itemDTO) {
        this.subcategory = new Subcategory(itemDTO.getSubcategory());
        this.title = itemDTO.getTitle();
        this.lotNo = itemDTO.getLotNo();
        this.description = itemDTO.getDescription();
        this.mlsLink = itemDTO.getMlsLink();
        this.price = itemDTO.getPrice();
        this.constructorInfo = itemDTO.getConstructorInfo();
        this.image = itemDTO.getImageString();
        this.creator = new User(itemDTO.getCreator());
        this.createTime = TimeUtil.convertStringToDate(itemDTO.getCreateTime());
        this.renter = itemDTO.getRenter() == null ? null : new User(itemDTO.getRenter());
        this.status = itemDTO.getStatus();
        this.itemType = itemDTO.getItemType();
    }

    public Item(String id, String title, int lotNo, String description, String mlsLink, String price, String constructorInfo,
                String image, Date createTime, String status, String itemType) {
        this.id = id;
        this.title = title;
        this.lotNo = lotNo;
        this.description = description;
        this.mlsLink = mlsLink;
        this.price = price;
        this.constructorInfo = constructorInfo;
        this.image = image;
        this.createTime = createTime;
        this.status = status;
        this.itemType = itemType;
    }

    public Item(User creator,
                String title,
                String description,
                int lotNo,
                String itemType,
                Subcategory subcategory) {
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.lotNo = lotNo;
        this.itemType=itemType;
        this.subcategory = subcategory;
        this.createTime = new Date();
        this.status = "Active";
    }
    public Item(User creator,
                String title,
                String description,
                int lotNo,
                String itemType,
                Subcategory subcategory,
                Date createTime) {
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.lotNo = lotNo;
        this.itemType=itemType;
        this.subcategory = subcategory;
        this.createTime = createTime;
        this.status = "Active";
    }

    public Item(NewItemRequest item, String type, String creatorId) {
        this.subcategory = new Subcategory(item.getSubcategoryId());
        this.title = item.getTitle();
        this.lotNo = item.getLotNo();
        this.description = item.getDescription();
        this.mlsLink = item.getMlsLink();
        this.price = item.getPrice();
        this.constructorInfo = item.getContactInfo();
        this.image = item.getImagesString();
        this.status = item.getStatus();
        this.itemType = type;
        this.creator = new User(creatorId);
        this.createTime = new Date();
    }

    public Item(String id, NewItemRequest item, String type, String creatorId) {
        this(item, type, creatorId);
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLotNo() {
        return lotNo;
    }

    public void setLotNo(int lotNo) {
        this.lotNo = lotNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMlsLink() {
        return mlsLink;
    }

    public void setMlsLink(String mlsLink) {
        this.mlsLink = mlsLink;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getConstructorInfo() {
        return constructorInfo;
    }

    public void setConstructorInfo(String constructorInfo) {
        this.constructorInfo = constructorInfo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}