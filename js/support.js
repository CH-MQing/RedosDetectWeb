const fs = require("fs");
var arguments = process.argv[2];
file_name = arguments;
const source = fs.readFileSync(file_name, {encoding: 'utf8'});
const lines = source.split("\n");
let regex = lines[0];
let modifiers = lines[1];
let pattern = new RegExp(regex);//定义正则
let prefix = lines[2];
let repeat = lines[3];
let suffix = lines[4];
let times = lines[5];
s = Date.now();
let str = prefix;
for (i = 0; i < times; i++) {
    str = str + repeat;
}
str = str + suffix;
str = str.replace(/\\n/g, '\n').replace(/\\v/g, '\v').replace(/\\r/g, '\r').replace(/\\f/g, '\f').replace(/\\\\/g, "\\");//规范格式，形成攻击字符串
let start = Date.now();
start = Date.now() - start;
pattern.test(str);//执行js下的攻击字符串的匹配过程
s = Date.now() - s;//计算匹配时间
console.log(s);