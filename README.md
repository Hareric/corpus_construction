# 语料库构建

## 项目说明
爬取[ted演讲][1]网站中的演讲文稿，并将外文与中文对应保存

## 爬取流程
![此处输入图片的描述][2]

## 示例
![transcript示例][3]

## 运行方法
运行`TranscriptDownload.java`
```
TranscriptDownload td = new TranscriptDownload();
td.multiDownload("id", "transcript/id", 5);  //外文简写，保存文件路径，线程个数 
```
马来西亚语 简写为ms

中文 简写为 zh-cn

具体简写可在[ted网站][5]上查询

## 
## 下载
部分爬取数据[下载][4]


  [1]: https://www.ted.com/talks
  [2]: http://oevwfwaro.bkt.clouddn.com/ted%20%E7%88%AC%E5%8F%96%E6%B5%81%E7%A8%8B%E5%9B%BE.png
  [3]: http://oevwfwaro.bkt.clouddn.com/transcript%20%E7%A4%BA%E4%BE%8B.png
  [4]: http://oevwfwaro.bkt.clouddn.com/transcript.zip
  [5]: https://www.ted.com/talks
