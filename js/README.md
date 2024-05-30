1. batch 用于批量处理正则表达式（需要手动执行，只执行验证阶段，检测阶段由Test.onlyCheck()完成）--用来测试代码写的是否对，并且输出的结果是txt文件
2. 其余代码用于配合ReDoSDetect完成多语言功能，无法单独执行，只能通过ReDoSDetect调用。
3. attack.js是执行js语言环境下Regex验证的主要程序
4. support.js是作为Attack.js执行的参数文件，主要执行正则匹配并记录时间

---