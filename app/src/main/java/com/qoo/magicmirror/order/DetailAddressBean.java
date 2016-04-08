package com.qoo.magicmirror.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dllo on 16/4/7.
 */
public class DetailAddressBean implements Parcelable{

    /**
     * result : 1
     * msg :
     * data : {"pagination":{"first_time":"1460027876","last_time":"","has_more":"2"},"list":[{"addr_id":"105","zip_code":"","username":"789745","cellphone":"6456","addr_info":"7898797","if_moren":"2","city":""},{"addr_id":"104","zip_code":"","username":"78987","cellphone":"46556","addr_info":"78989","if_moren":"2","city":""},{"addr_id":"103","zip_code":"","username":"123","cellphone":"4564","addr_info":"7896","if_moren":"2","city":""},{"addr_id":"102","zip_code":"","username":"sandy","cellphone":"13934964737","addr_info":"山西大同","if_moren":"1","city":""},{"addr_id":"101","zip_code":"","username":"123","cellphone":"123","addr_info":"123","if_moren":"2","city":""}]}
     */

    private String result;
    private String msg;
    /**
     * pagination : {"first_time":"1460027876","last_time":"","has_more":"2"}
     * list : [{"addr_id":"105","zip_code":"","username":"789745","cellphone":"6456","addr_info":"7898797","if_moren":"2","city":""},{"addr_id":"104","zip_code":"","username":"78987","cellphone":"46556","addr_info":"78989","if_moren":"2","city":""},{"addr_id":"103","zip_code":"","username":"123","cellphone":"4564","addr_info":"7896","if_moren":"2","city":""},{"addr_id":"102","zip_code":"","username":"sandy","cellphone":"13934964737","addr_info":"山西大同","if_moren":"1","city":""},{"addr_id":"101","zip_code":"","username":"123","cellphone":"123","addr_info":"123","if_moren":"2","city":""}]
     */

    private DataBean data;

    protected DetailAddressBean(Parcel in) {
        result = in.readString();
        msg = in.readString();
    }

    public static final Creator<DetailAddressBean> CREATOR = new Creator<DetailAddressBean>() {
        @Override
        public DetailAddressBean createFromParcel(Parcel in) {
            return new DetailAddressBean(in);
        }

        @Override
        public DetailAddressBean[] newArray(int size) {
            return new DetailAddressBean[size];
        }
    };

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(msg);
    }

    public static class DataBean implements Parcelable{
        /**
         * first_time : 1460027876
         * last_time :
         * has_more : 2
         */

        private PaginationBean pagination;
        /**
         * addr_id : 105
         * zip_code :
         * username : 789745
         * cellphone : 6456
         * addr_info : 7898797
         * if_moren : 2
         * city :
         */

        private List<ListBean> list;

        protected DataBean(Parcel in) {
            pagination = in.readParcelable(PaginationBean.class.getClassLoader());
            list = in.createTypedArrayList(ListBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(pagination, flags);
            dest.writeTypedList(list);
        }

        public static class PaginationBean implements Parcelable{
            private String first_time;
            private String last_time;
            private String has_more;

            protected PaginationBean(Parcel in) {
                first_time = in.readString();
                last_time = in.readString();
                has_more = in.readString();
            }

            public static final Creator<PaginationBean> CREATOR = new Creator<PaginationBean>() {
                @Override
                public PaginationBean createFromParcel(Parcel in) {
                    return new PaginationBean(in);
                }

                @Override
                public PaginationBean[] newArray(int size) {
                    return new PaginationBean[size];
                }
            };

            public String getFirst_time() {
                return first_time;
            }

            public void setFirst_time(String first_time) {
                this.first_time = first_time;
            }

            public String getLast_time() {
                return last_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }

            public String getHas_more() {
                return has_more;
            }

            public void setHas_more(String has_more) {
                this.has_more = has_more;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(first_time);
                dest.writeString(last_time);
                dest.writeString(has_more);
            }
        }

        public static class ListBean implements Parcelable{
            private String addr_id;
            private String zip_code;
            private String username;
            private String cellphone;
            private String addr_info;
            private String if_moren;
            private String city;

            protected ListBean(Parcel in) {
                addr_id = in.readString();
                zip_code = in.readString();
                username = in.readString();
                cellphone = in.readString();
                addr_info = in.readString();
                if_moren = in.readString();
                city = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public String getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
            }

            public String getZip_code() {
                return zip_code;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getCellphone() {
                return cellphone;
            }

            public void setCellphone(String cellphone) {
                this.cellphone = cellphone;
            }

            public String getAddr_info() {
                return addr_info;
            }

            public void setAddr_info(String addr_info) {
                this.addr_info = addr_info;
            }

            public String getIf_moren() {
                return if_moren;
            }

            public void setIf_moren(String if_moren) {
                this.if_moren = if_moren;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(addr_id);
                dest.writeString(zip_code);
                dest.writeString(username);
                dest.writeString(cellphone);
                dest.writeString(addr_info);
                dest.writeString(if_moren);
                dest.writeString(city);
            }
        }
    }
}
