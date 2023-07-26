package com.sample;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.network.RxNetWork;
import io.reactivex.network.RxNetWorkListener;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * by y.
 * <p>
 * Description:
 */
public class SampleRxJava2Null {


    public static void start(boolean flag) {
        if (flag) {
            Observable<Object> map = RxNetWork.observable(Server.class).test(1).map(new SampleFun<>());
            RxNetWork
                    .getInstance()
                    .getApi(map, new RxNetWorkListener<Object>() {
                        @Override
                        public void onNetWorkStart() {

                        }

                        @Override
                        public void onNetWorkError(Throwable e) {
                        }

                        @Override
                        public void onNetWorkComplete() {

                        }

                        @Override
                        public void onNetWorkSuccess(Object data) {
                        }
                    });
        } else {
            RxNetWork
                    .getInstance()
                    .getApi(RxNetWork.observable(Server.class).testNull(), new RxNetWorkListener<Void>() {
                        @Override
                        public void onNetWorkStart() {

                        }

                        @Override
                        public void onNetWorkError(Throwable e) {
                        }

                        @Override
                        public void onNetWorkComplete() {

                        }

                        @Override
                        public void onNetWorkSuccess(Void data) {
                        }
                    });
        }
    }


    /**
     * RxJava2不能onNext(null)
     * 否则会报  Null is not a valid element
     * <p>
     * 一般网络请求会有code，message,data，
     * 但是有些接口例如token或者一些post或者delete不需要data，那解析的时候就会是Null
     * <p>
     * 例如示例{@link Server#testNull()},如果直接返回Void。则会直接报  Null is not a valid element
     * 如果没问题的话,例如{@link Server#test(int)} ()}则会 在{@link RxNetWorkListener#onNetWorkSuccess(Object)}打印 成功之后的数据
     * <p>
     * 这个示例的目的是为了防止onNext(null)这个情况发生
     * 如果打印成功之后的数据  则示例成功
     * 如果 Null is not a valid element 则是反面示例
     * <p>
     * <p>
     * 这里需要确定的是如果data一直为null的话，则可以用 Object 接收 data, 在 数据检查的时候直接使用
     * <pre>
     *     (T) new TypeToken<T>() {}.getType()
     * </pre>
     * 即可
     * <p>
     * 否则会报 libcore.reflect.TypeVariableImpl cannot be cast to xxEntity 异常
     * 当然了，如果 data 在某些情况下为 null 的话，则可以直接返回 BaseEntity 或者再使用一个实体类把他们包裹起来，让 onNext 不要返回 null 即可，
     * 在某些情况下为 null 的情况下，建议和后台商量下，规范最重要，方便大家
     * 这里为了方便直接使用了Object 接收
     */

    interface Server {
        //直接返回 Void 则会报错
        @GET("https://www.baidu.com")
        Observable<Void> testNull();

        @GET("http://api.codekk.com/op/page/" + "{page}")
        Observable<SampleEntity<Object>> test(@Path("page") int page);
    }

    private static class SampleObservableOperator<D, U> implements ObservableOperator<D, U> {

        @Override
        public Observer<? super U> apply(Observer<? super D> observer) {
            return new Observer<U>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(U u) {
                    if (u != null && u instanceof SampleEntity) {
                        // code不对直接异常


                        Object data = ((SampleEntity) u).getData();
                        data = null;
                        //这里手动设置为Null，则会报 Null is not a valid element
                        if (data == null) {
                            observer.onNext((D) new TypeToken<D>() {
                            }.getType());
                        } else {
                            observer.onNext((D) data);
                        }
                    } else {
                        observer.onError(new RuntimeException());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    observer.onError(e);
                }

                @Override
                public void onComplete() {
                    observer.onComplete();
                }
            };
        }
    }


    private static class SampleFun<T> implements Function<SampleEntity<T>, T> {

        @Override
        public T apply(SampleEntity<T> tSampleEntity) {
            // 这里根据code判断，如果不对直接强制异常即可
            return (T) new TypeToken<T>() {
            }.getType();
        }
    }


    private static class SampleEntity<T> {
        private int code;
        private String message;
        private T data;

        public SampleEntity(int code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


    private static class OpListModel {


        private List<ProjectArrayBean> projectArray;

        public List<ProjectArrayBean> getProjectArray() {
            return projectArray;
        }

        public void setProjectArray(List<ProjectArrayBean> projectArray) {
            this.projectArray = projectArray;
        }

        public static class ProjectArrayBean {

            private String projectName;
            private String createTime;
            private String updateTime;
            private int expiredTimes;
            private int usedTimes;
            private int voteUp;
            private boolean recommend;
            private boolean hide;
            private String projectUrl;
            private String demoUrl;
            private String committer;
            private String source;
            private String lang;
            private String authorName;
            private String authorUrl;
            private String codeKKUrl;
            private String _id;
            private String desc;
            private Object officialUrl;
            private List<TagsBean> tags;

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getExpiredTimes() {
                return expiredTimes;
            }

            public void setExpiredTimes(int expiredTimes) {
                this.expiredTimes = expiredTimes;
            }

            public int getUsedTimes() {
                return usedTimes;
            }

            public void setUsedTimes(int usedTimes) {
                this.usedTimes = usedTimes;
            }

            public int getVoteUp() {
                return voteUp;
            }

            public void setVoteUp(int voteUp) {
                this.voteUp = voteUp;
            }

            public boolean isRecommend() {
                return recommend;
            }

            public void setRecommend(boolean recommend) {
                this.recommend = recommend;
            }

            public boolean isHide() {
                return hide;
            }

            public void setHide(boolean hide) {
                this.hide = hide;
            }

            public String getProjectUrl() {
                return projectUrl;
            }

            public void setProjectUrl(String projectUrl) {
                this.projectUrl = projectUrl;
            }

            public String getDemoUrl() {
                return demoUrl;
            }

            public void setDemoUrl(String demoUrl) {
                this.demoUrl = demoUrl;
            }

            public String getCommitter() {
                return committer;
            }

            public void setCommitter(String committer) {
                this.committer = committer;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            public String getAuthorUrl() {
                return authorUrl;
            }

            public void setAuthorUrl(String authorUrl) {
                this.authorUrl = authorUrl;
            }

            public String getCodeKKUrl() {
                return codeKKUrl;
            }

            public void setCodeKKUrl(String codeKKUrl) {
                this.codeKKUrl = codeKKUrl;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public Object getOfficialUrl() {
                return officialUrl;
            }

            public void setOfficialUrl(Object officialUrl) {
                this.officialUrl = officialUrl;
            }

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public static class TagsBean {
                private String createTime;
                private String name;
                private String userName;
                private String type;

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }

}
