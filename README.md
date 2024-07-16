# CDR springboot(maven)+JDBC(Oracle)
业务逻辑为：通过Postman输入参数，在指定时间范围内以指定的频率，执行对Oracle数据库的事务操作，每次事务操作的结果会写入数据日志里，事务的执行情况都会记录在任务日志里。
使用动态数据源，检查两个分别在不同用户下的表，将表中的符合规则的数量等信息写入数据日志表中，将不符合规则的具体记录写入数据明细表中；事务还会记录自己的执行时长和执行是否成功，并将其写入任务日志表和任务明细表中。这四个表关系为：任务日志表的主键是任务明细表和数据日志表里外键的依赖，数据日志表的主键是数据明细表外键的依赖；一个任务记录对应多任务明细和数据日志记录，一个数据日志记录对应多个数据明细。

该业务会在用户设定的时间范围内以用户设定的频率反复运行任务“CDR任务”，该CDR任务会对表PATIENT1执行3种数据库操作，用户会输入开始时间、结束时间和院区名称筛选记录，后续只处理CREATE_DATA大于开始时间小于结束时间且YQ_NAME与院区名称一致的筛选记录，这3种操作的名称分别为：“身份证号的完整性”，规则要求字段ID_NO的内容不为null；“年龄的数值范围”，规则要求字段Age的数值大于0小于100；“就诊号和就诊日期的唯一性”，规则要求字段PATIENT_ID和CREATED_DATE的内容不重复。还会对表PATIENT2执行1种操作，就是统计表中符合筛选条件的记录数量。PATIENT1、PATIENT2表的字段有PATIENYT_ID、NAME、ID_NO、ADDRESS、PHONE_NO、SEX_NAME、BIRTHD_DATE、CREATE_DATE、AGE和YQ_NAME。
在该业务下每执行完一次任务，会在TASK_LOGS表里写入：主键ID自增，TASK_NAME="CDR任务",RUN_TIME=任务运行开始时间，RUN_STATUS=任务运行是否成功，RUN_DURATION=任务运行耗时。
在该任务下每执行完3种操作中的一种操作：
1.会在TASK_DETAILS表里写入：主键ID自增，外键TASK_ID依赖于TASK_LOGS表的主键ID，RUN_NAME=该操作的名称、RUN_TIME=操作运行开始时间，RUN_STATUS=操作执行是否成功和LOG_DESCRIPTION=“运行监测成功”或错误信息描述；
2.会在DATA_LOGS表里写入：主键ID自增，外键TASK_ID依赖于TASK_LOGS表的主键ID，RUN_TIME=操作开始执行时间，TASK_NAME=“CDR任务”，RULE_NAME=该操作的名称，AUDIT_NAME=符合该操作的规则要求的记录的数量，BASE_VALUE=基础数值，ANOMALY_VALUE=不符合该操作的规则要求的记录的数量，TIME_RANGE记录“操作开始时间 至 操作结束时间”;
3.会将PATIENT1表中不符合该操作的规则的具体记录在DATA_DETAILS中备份：主键ID自增，PATIENT_ID相同，外键TASK_ID依赖于TASK_LOG表的主键ID，PATIENT_ID相同，NAME相同，ID_NO相同，ADDRESS相同，PHONE_NO相同，SEX_NAME相同，BIRTH_DATE相同，CREATE_DATE相同，AGE相同，YQ_NAME相同。
其余为零散的查表功能。核心代码在TaskService、TaskRepository里。以后业务不要用JDBC坑太多，建议都用JPA。
非常好的shit moutain，改了十几遍屹立不倒。
