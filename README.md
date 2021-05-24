# eazy-tool
some tools by java   

## Print a table on console
```java
final List<Obj> source = Stream.generate(Obj::new)
        .limit(3)
        .collect(Collectors.toList());

DataTable.from(source)
        .printer(PrintSetting.builder()
        .align(Align.Center)
        .truncTooLong(true)
        .showIndex(true)
        .truncLimitWidth(100).build())
        .addTypeWriter(Calendar.class, obj -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj.getTime()))
        .write(new PrintWriter(System.out));
```
