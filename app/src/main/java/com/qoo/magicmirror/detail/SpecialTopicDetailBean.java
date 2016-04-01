package com.qoo.magicmirror.detail;

import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */

public class SpecialTopicDetailBean {



    private String result;
    private String msg;


    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String story_title;
        private String story_des;
        private String story_img;
        private String story_id;
        private String if_original;
        private String original_url;
        private String from;
        private String story_url;


        private StoryDataEntity story_data;

        public void setStory_title(String story_title) {
            this.story_title = story_title;
        }

        public void setStory_des(String story_des) {
            this.story_des = story_des;
        }

        public void setStory_img(String story_img) {
            this.story_img = story_img;
        }

        public void setStory_id(String story_id) {
            this.story_id = story_id;
        }

        public void setIf_original(String if_original) {
            this.if_original = if_original;
        }

        public void setOriginal_url(String original_url) {
            this.original_url = original_url;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setStory_url(String story_url) {
            this.story_url = story_url;
        }

        public void setStory_data(StoryDataEntity story_data) {
            this.story_data = story_data;
        }

        public String getStory_title() {
            return story_title;
        }

        public String getStory_des() {
            return story_des;
        }

        public String getStory_img() {
            return story_img;
        }

        public String getStory_id() {
            return story_id;
        }

        public String getIf_original() {
            return if_original;
        }

        public String getOriginal_url() {
            return original_url;
        }

        public String getFrom() {
            return from;
        }

        public String getStory_url() {
            return story_url;
        }

        public StoryDataEntity getStory_data() {
            return story_data;
        }

        public static class StoryDataEntity {
            private String story_date_type;
            private String story_date_url;
            private String title;
            private String subtitle;
            private String head_img;
            private String if_suggest;
            private List<?> goods_data;
            /**
             * verticalTitle :
             * verticalTitleColor :
             * smallTitle : 探究Marcel Floruss的私服搭配
             * title : 全世界最會穿衣服的苦臉博主
             * titleColor : fffffff
             * subTitle : 因为拍照不笑，被大家称为“苦脸博主”的Marcel Floruss，是一个纽约时装学院的学生，他拥有模特般的高挑身材以及立体帅气的五官。他的私服一向备受好评，他的出镜单品可能是Marc by Marc Jacobs，也有可能是H&M。Marcel Floruss巧妙地将古着与快时尚、高定单品相融合，既有熟男的庄重，也有街头的潮流。
             * colorTitle :
             * colorTitleColor : 6c1836ff
             * category : styleTwo
             * info_if_tag : 2
             * goodsname :
             * goodsprice :
             * goodsx :
             * goodsY :
             * good_info : {"goods_id":"","goods_pic":"","model":"","goods_img":"","goods_name":"","last_storge":"","whole_storge":"","height":"","ordain":"","product_area":"","goods_price":"","discount_price":"","brand":"","info_des":"","goods_data":[{"introContent":"","cellHeight":"","name":"","location":"","country":"","english":""}],"design_des":[{"img":"","cellHeight":"","type":""}],"goods_share":"http://api101.test.mirroreye.cn/index.php/goodweb/info?id="}
             */

            private List<TextArrayEntity> text_array;
            private List<String> img_array;

            public void setStory_date_type(String story_date_type) {
                this.story_date_type = story_date_type;
            }

            public void setStory_date_url(String story_date_url) {
                this.story_date_url = story_date_url;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public void setIf_suggest(String if_suggest) {
                this.if_suggest = if_suggest;
            }

            public void setGoods_data(List<?> goods_data) {
                this.goods_data = goods_data;
            }

            public void setText_array(List<TextArrayEntity> text_array) {
                this.text_array = text_array;
            }

            public void setImg_array(List<String> img_array) {
                this.img_array = img_array;
            }

            public String getStory_date_type() {
                return story_date_type;
            }

            public String getStory_date_url() {
                return story_date_url;
            }

            public String getTitle() {
                return title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public String getHead_img() {
                return head_img;
            }

            public String getIf_suggest() {
                return if_suggest;
            }

            public List<?> getGoods_data() {
                return goods_data;
            }

            public List<TextArrayEntity> getText_array() {
                return text_array;
            }

            public List<String> getImg_array() {
                return img_array;
            }

            public static class TextArrayEntity {
                private String verticalTitle;
                private String verticalTitleColor;
                private String smallTitle;
                private String title;
                private String titleColor;
                private String subTitle;
                private String colorTitle;
                private String colorTitleColor;
                private String category;
                private String info_if_tag;
                private String goodsname;
                private String goodsprice;
                private String goodsx;
                private String goodsY;
                /**
                 * goods_id :
                 * goods_pic :
                 * model :
                 * goods_img :
                 * goods_name :
                 * last_storge :
                 * whole_storge :
                 * height :
                 * ordain :
                 * product_area :
                 * goods_price :
                 * discount_price :
                 * brand :
                 * info_des :
                 * goods_data : [{"introContent":"","cellHeight":"","name":"","location":"","country":"","english":""}]
                 * design_des : [{"img":"","cellHeight":"","type":""}]
                 * goods_share : http://api101.test.mirroreye.cn/index.php/goodweb/info?id=
                 */

                private GoodInfoEntity good_info;

                public void setVerticalTitle(String verticalTitle) {
                    this.verticalTitle = verticalTitle;
                }

                public void setVerticalTitleColor(String verticalTitleColor) {
                    this.verticalTitleColor = verticalTitleColor;
                }

                public void setSmallTitle(String smallTitle) {
                    this.smallTitle = smallTitle;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setTitleColor(String titleColor) {
                    this.titleColor = titleColor;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public void setColorTitle(String colorTitle) {
                    this.colorTitle = colorTitle;
                }

                public void setColorTitleColor(String colorTitleColor) {
                    this.colorTitleColor = colorTitleColor;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public void setInfo_if_tag(String info_if_tag) {
                    this.info_if_tag = info_if_tag;
                }

                public void setGoodsname(String goodsname) {
                    this.goodsname = goodsname;
                }

                public void setGoodsprice(String goodsprice) {
                    this.goodsprice = goodsprice;
                }

                public void setGoodsx(String goodsx) {
                    this.goodsx = goodsx;
                }

                public void setGoodsY(String goodsY) {
                    this.goodsY = goodsY;
                }

                public void setGood_info(GoodInfoEntity good_info) {
                    this.good_info = good_info;
                }

                public String getVerticalTitle() {
                    return verticalTitle;
                }

                public String getVerticalTitleColor() {
                    return verticalTitleColor;
                }

                public String getSmallTitle() {
                    return smallTitle;
                }

                public String getTitle() {
                    return title;
                }

                public String getTitleColor() {
                    return titleColor;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public String getColorTitle() {
                    return colorTitle;
                }

                public String getColorTitleColor() {
                    return colorTitleColor;
                }

                public String getCategory() {
                    return category;
                }

                public String getInfo_if_tag() {
                    return info_if_tag;
                }

                public String getGoodsname() {
                    return goodsname;
                }

                public String getGoodsprice() {
                    return goodsprice;
                }

                public String getGoodsx() {
                    return goodsx;
                }

                public String getGoodsY() {
                    return goodsY;
                }

                public GoodInfoEntity getGood_info() {
                    return good_info;
                }

                public static class GoodInfoEntity {
                    private String goods_id;
                    private String goods_pic;
                    private String model;
                    private String goods_img;
                    private String goods_name;
                    private String last_storge;
                    private String whole_storge;
                    private String height;
                    private String ordain;
                    private String product_area;
                    private String goods_price;
                    private String discount_price;
                    private String brand;
                    private String info_des;
                    private String goods_share;
                    /**
                     * introContent :
                     * cellHeight :
                     * name :
                     * location :
                     * country :
                     * english :
                     */

                    private List<GoodsDataEntity> goods_data;
                    /**
                     * img :
                     * cellHeight :
                     * type :
                     */

                    private List<DesignDesEntity> design_des;

                    public void setGoods_id(String goods_id) {
                        this.goods_id = goods_id;
                    }

                    public void setGoods_pic(String goods_pic) {
                        this.goods_pic = goods_pic;
                    }

                    public void setModel(String model) {
                        this.model = model;
                    }

                    public void setGoods_img(String goods_img) {
                        this.goods_img = goods_img;
                    }

                    public void setGoods_name(String goods_name) {
                        this.goods_name = goods_name;
                    }

                    public void setLast_storge(String last_storge) {
                        this.last_storge = last_storge;
                    }

                    public void setWhole_storge(String whole_storge) {
                        this.whole_storge = whole_storge;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public void setOrdain(String ordain) {
                        this.ordain = ordain;
                    }

                    public void setProduct_area(String product_area) {
                        this.product_area = product_area;
                    }

                    public void setGoods_price(String goods_price) {
                        this.goods_price = goods_price;
                    }

                    public void setDiscount_price(String discount_price) {
                        this.discount_price = discount_price;
                    }

                    public void setBrand(String brand) {
                        this.brand = brand;
                    }

                    public void setInfo_des(String info_des) {
                        this.info_des = info_des;
                    }

                    public void setGoods_share(String goods_share) {
                        this.goods_share = goods_share;
                    }

                    public void setGoods_data(List<GoodsDataEntity> goods_data) {
                        this.goods_data = goods_data;
                    }

                    public void setDesign_des(List<DesignDesEntity> design_des) {
                        this.design_des = design_des;
                    }

                    public String getGoods_id() {
                        return goods_id;
                    }

                    public String getGoods_pic() {
                        return goods_pic;
                    }

                    public String getModel() {
                        return model;
                    }

                    public String getGoods_img() {
                        return goods_img;
                    }

                    public String getGoods_name() {
                        return goods_name;
                    }

                    public String getLast_storge() {
                        return last_storge;
                    }

                    public String getWhole_storge() {
                        return whole_storge;
                    }

                    public String getHeight() {
                        return height;
                    }

                    public String getOrdain() {
                        return ordain;
                    }

                    public String getProduct_area() {
                        return product_area;
                    }

                    public String getGoods_price() {
                        return goods_price;
                    }

                    public String getDiscount_price() {
                        return discount_price;
                    }

                    public String getBrand() {
                        return brand;
                    }

                    public String getInfo_des() {
                        return info_des;
                    }

                    public String getGoods_share() {
                        return goods_share;
                    }

                    public List<GoodsDataEntity> getGoods_data() {
                        return goods_data;
                    }

                    public List<DesignDesEntity> getDesign_des() {
                        return design_des;
                    }

                    public static class GoodsDataEntity {
                        private String introContent;
                        private String cellHeight;
                        private String name;
                        private String location;
                        private String country;
                        private String english;

                        public void setIntroContent(String introContent) {
                            this.introContent = introContent;
                        }

                        public void setCellHeight(String cellHeight) {
                            this.cellHeight = cellHeight;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public void setLocation(String location) {
                            this.location = location;
                        }

                        public void setCountry(String country) {
                            this.country = country;
                        }

                        public void setEnglish(String english) {
                            this.english = english;
                        }

                        public String getIntroContent() {
                            return introContent;
                        }

                        public String getCellHeight() {
                            return cellHeight;
                        }

                        public String getName() {
                            return name;
                        }

                        public String getLocation() {
                            return location;
                        }

                        public String getCountry() {
                            return country;
                        }

                        public String getEnglish() {
                            return english;
                        }
                    }

                    public static class DesignDesEntity {
                        private String img;
                        private String cellHeight;
                        private String type;

                        public void setImg(String img) {
                            this.img = img;
                        }

                        public void setCellHeight(String cellHeight) {
                            this.cellHeight = cellHeight;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }

                        public String getImg() {
                            return img;
                        }

                        public String getCellHeight() {
                            return cellHeight;
                        }

                        public String getType() {
                            return type;
                        }
                    }
                }
            }
        }
    }
}
