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
╒═══════╤══════════════════════╤═════════════╤═════════════╤═════════════════╤══════════════════════╤══════════════════════╕
│       │     testAString      │ testBString │ testCString │ testCStringLong │       calendar       │   testDStringLong    │
╞═══════╪══════════════════════╪═════════════╪═════════════╪═════════════════╪══════════════════════╪══════════════════════╡
│   0   │ 43t24rv54c51345c=2.3 │    44r24    │    44r24    │                 │ 2021-05-24 17:16:46  │ sdfasdfda sdfdfasdfw │
│       │         4r24         │             │             │                 │                      │ ae4fda fdsafsdfadfas │
│       │                      │             │             │                 │                      │ dfasdfas drq234r34c  │
├───────┼──────────────────────┼─────────────┼─────────────┼─────────────────┼──────────────────────┼──────────────────────┤
│   1   │ 43t24rv54c51345c=2.3 │    44r24    │    44r24    │                 │ 2021-05-24 17:16:46  │ sdfasdfda sdfdfasdfw │
│       │         4r24         │             │             │                 │                      │ ae4fda fdsafsdfadfas │
│       │                      │             │             │                 │                      │ dfasdfas drq234r34c  │
├───────┼──────────────────────┼─────────────┼─────────────┼─────────────────┼──────────────────────┼──────────────────────┤
│   2   │ 43t24rv54c51345c=2.3 │    44r24    │    44r24    │                 │ 2021-05-24 17:16:46  │ sdfasdfda sdfdfasdfw │
│       │         4r24         │             │             │                 │                      │ ae4fda fdsafsdfadfas │
│       │                      │             │             │                 │                      │ dfasdfas drq234r34c  │
└───────┴──────────────────────┴─────────────┴─────────────┴─────────────────┴──────────────────────┴──────────────────────┘

