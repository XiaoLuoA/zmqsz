package cn.edu.vote.model;

import org.json.JSONObject;

import javax.persistence.*;

/**
 * @author zzu
 */
@Entity
@Table(name = "teacher", schema = "favorite")
public class TeacherEntity {
    private int id;
    private String name;
    private String department;
    private String pic;
    private String detail;
    private int num;
    private int delta;
    private  String blank1;
    private String blank2;

    public TeacherEntity() {
        this.delta = 0;
    }

    public TeacherEntity(int id, String name, String department, String pic, String detail, int num , String blank1 , String blank2) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.pic = pic;
        this.detail = detail;
        this.num = num;
        this.delta = 0;
        this.blank1 = blank1;
        this.blank2 = blank2;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    @Transient
    public int getDelta() {
        return delta;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "department", nullable = false, length = 20)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "pic")
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Basic
    @Column(name = "blank1", length = 50)
    public String getBlank1() {
        return blank1;
    }

    public void setBlank1(String blank1) {
        this.blank1 = blank1;
    }

    @Basic
    @Column(name = "blank2", length = 50)
    public String getBlank2() {
        return blank2;
    }

    public void setBlank2(String blank2) {
        this.blank2 = blank2;
    }

    @Basic
    @Column(name = "detail", length = 800)
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Basic
    @Column(name = "num")
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 票数变化量加1
     * 考虑到可能有多台服务器同时工作
     * 每次要记录票数的变化量
     */
    public void add() {
        delta++;
    }

    @Override
    public String toString() {
        return (new JSONObject(this)).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeacherEntity)) {
            return false;
        }
        TeacherEntity that = (TeacherEntity) o;
        if (id != that.id) {
            return false;
        }
        if (num != that.num) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (department != null ? !department.equals(that.department) : that.department != null) {
            return false;
        }
        if (blank1 != null ? !blank1.equals(that.blank1) : that.blank1 != null) {
            return false;
        }
        if (blank2 != null ? !blank2.equals(that.blank2) : that.blank2 != null) {
            return false;
        }

        return (pic != null ? pic.equals(that.pic) : that.pic == null) && (detail != null ? detail.equals(that.detail) : that.detail == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (blank1 != null ? blank1.hashCode() : 0);
        result = 31 * result + (blank2 != null ? blank2.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + num;
        return result;
    }
}
