package cn.edu.vote.model;

import org.json.JSONObject;

import javax.persistence.*;

/**
 * @author zzu
 */
@Entity
@Table(name = "user", schema = "favorite")
public class UserEntity {
    private String openid;
    private int day;

    public UserEntity() {}

    public UserEntity(String openid, int day) {
        this.openid = openid;
        this.day = day;
    }

    @Id
    @Column(name = "openid", nullable = false, length = 100)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "day", nullable = false)
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (day != that.day) return false;
        return openid != null ? openid.equals(that.openid) : that.openid == null;
    }

    @Override
    public int hashCode() {
        int result = openid != null ? openid.hashCode() : 0;
        result = 31 * result + day;
        return result;
    }

    @Override
    public String toString() {
        return (new JSONObject(this)).toString();
    }
}
