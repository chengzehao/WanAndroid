package com.business.wanandroid.bean;

import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2019/2/20/020 19:52
 */

public class CollectBean {
    /**
     * curPage : 1
     * datas : [{"author":"DeltaTech","chapterId":173,"chapterName":"Choreographer","courseId":13,"desc":"","envelopePic":"","id":47054,"link":"https://www.jianshu.com/p/996bca12eb1d","niceDate":"4小时前","origin":"","originId":7840,"publishTime":1550648194000,"title":"Android Choreographer 源码分析","userId":4714,"visible":0,"zan":0},{"author":"何时夕","chapterId":40,"chapterName":"Context","courseId":13,"desc":"","envelopePic":"","id":47012,"link":"https://juejin.im/post/5c6a2e97f265da2dd37c0d82","niceDate":"6小时前","origin":"","originId":7956,"publishTime":1550640839000,"title":"四大组件以及 Application和Context的全面理解","userId":4714,"visible":0,"zan":0},{"author":"Ruheng","chapterId":26,"chapterName":"基础UI控件","courseId":13,"desc":"详解Android图文混排实现。","envelopePic":"","id":46946,"link":"http://www.jianshu.com/p/6843f332c8df","niceDate":"22小时前","origin":"","originId":1165,"publishTime":1550580653000,"title":"Android图文混排实现方式详解","userId":4714,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":43743,"link":"https://mp.weixin.qq.com/s/798YLxjb7LU0g4zNhKSrLg","niceDate":"2019-01-16","origin":"","originId":7804,"publishTime":1547608977000,"title":"又...又推荐一波优质资源","userId":4714,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 4
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * author : DeltaTech
         * chapterId : 173
         * chapterName : Choreographer
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 47054
         * link : https://www.jianshu.com/p/996bca12eb1d
         * niceDate : 4小时前
         * origin :
         * originId : 7840
         * publishTime : 1550648194000
         * title : Android Choreographer 源码分析
         * userId : 4714
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
