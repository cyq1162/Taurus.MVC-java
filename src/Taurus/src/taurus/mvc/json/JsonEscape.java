package taurus.mvc.json;

class JsonEscape {

	/**
	 * 编码
	 * @param value
	 * @return
	 */
	 public static String enEscape(String value, EscapeOp op)
     {
         if (op == EscapeOp.No) { return value; }
         boolean isInsert = false;
         int len = value.length();
         StringBuilder sb = new StringBuilder(len + 10);
         for (int i = 0; i < len; i++)
         {
             char c = value.charAt(i);
//             if (op == EscapeOp.Encode)
//             {
//                 if (c < 32 || c == '"' || c == '\\')
//                 {
//                     sb.appendFormat("@#{0}#@", (int)c);
//                     isInsert = true;
//                 }
//                 else { sb.Append(c); }
//                 continue;
//             }

             if (c < 32)
             {
                 switch (c)
                 {
                     case '\n':
                         sb.append("\\n");//直接替换追加。
                         break;
                     case '\t':
                         if (op == EscapeOp.Yes)
                         {
                             sb.append("\\t");//直接替换追加
                         }
                         break;
                     case '\r':
                         if (op == EscapeOp.Yes)
                         {
                             sb.append("\\r");//直接替换追加
                         }
                         break;
                     default:
                         break;
                 }
                 // '\n'=10  '\r'=13 '\t'=9 都会被过滤。
                 isInsert = true;
                 continue;
             }
             switch (c)
             {
                 case '"':
                     isInsert = true;
                     sb.append("\\");
                     break;
                 case '\\':
                     boolean isOK = op == EscapeOp.Yes;// || (i != 0 && i == len - 1 && value[i - 1] != '\\');// 如果是以\结尾,（非\\结尾时, 这部分是强制转，不会然影响整体格式）
                     if (!isOK && op == EscapeOp.Default && len > 1 && i < len - 1)//中间
                     {
                         switch (value.charAt(i+1))
                         {
                             // case '"':
                             case 'n':
                                 //case 'r'://r和t要转义，不过出事。
                                 //case 't':
                                 break;
                             default:
                                 isOK = true;
                                 break;
                         }
                     }
                     if (isOK)
                     {
                         isInsert = true;
                         sb.append("\\");
                     }
                     break;
             }
             sb.append(c);
         }

         if (isInsert)
         {
             value = null;
             value = sb.toString();
         }
         else { sb = null; }
         return value;
     }
	 /**
    * 解码
    * @param result
    * @param op
    * @return
    */
     public static String unEscape(String result, EscapeOp op)
     {
         if (op == EscapeOp.No) { return result; }
//         if (op == EscapeOp.Encode)
//         {
//             if (result.IndexOf("@#") > -1 && result.IndexOf("#@") > -1) // 先解系统编码
//             {
//                 MatchCollection matchs = Regex.Matches(result, @"@#(\d{1,2})#@", RegexOptions.Compiled);
//                 if (matchs != null && matchs.Count > 0)
//                 {
//                     List<string> keys = new List<string>(matchs.Count);
//                     foreach (Match match in matchs)
//                     {
//                         if (match.Groups.Count > 1)
//                         {
//                             int code = int.Parse(match.Groups[1].Value);
//                             string charText = ((char)code).ToString();
//                             result = result.Replace(match.Groups[0].Value, charText);
//                         }
//                     }
//                 }
//             }
//             return result;
//         }
         if (result.indexOf("\\") > -1)
         {
             boolean has = result.indexOf("\\\\") > -1;
             if (has)
             {
                 result = result.replace("\\\\", "#&#=#");
             }
             if (op == EscapeOp.Yes)
             {
                 result = result.replace("\\t", "\t").replace("\\r", "\r");
             }
             result = result.replace("\\\"", "\"").replace("\\n", "\n");//.Replace("\\\\","\\");
             if (has)
             {
                 result = result.replace("#&#=#", "\\");
             }
         }
         return result;
     }
 
}
