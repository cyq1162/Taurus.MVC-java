package taurus.mvc.json;

/// <summary>
/// �ַ�״̬
/// </summary>
class CharState
{
    char lastKeywordChar = ' ';
    char lastChar = ' ';
    /// <summary>
    /// �Ƿ��ʽ��ʽ��true���Ա���˫���ţ�false���Կ��Ե����ź������š���
    /// </summary>
    boolean isStrictMode = false;
    public CharState(boolean isStrictMode)
    {
        this.isStrictMode = isStrictMode;
    }
    boolean jsonStart = false;//�� "{"��ʼ��...
    boolean setDicValue = false;// ���������ֵ�ֵ�ˡ�
    boolean escapeChar = false;//��"\"ת����ſ�ʼ��
    /// <summary>
    /// ���鿪ʼ������һ��ͷ���㡿��ֵǶ�׵��ԡ�childrenStart������ʶ��
    /// </summary>
    boolean arrayStart = false;//��"[" ���ſ�ʼ��
    boolean childrenStart = false;//�Ӽ�Ƕ�׿�ʼ�ˡ�
    /// <summary>
    /// ��-1 δ��ʼ������0ȡ���׶Ρ���1 ȡֵ�׶Ρ�
    /// </summary>
    int keyValueState = -1;

    /// <summary>
    /// ��-2 �ѽ�������-1 δ��ʼ������0 δ��ʼ����1 �����ſ�ʼ����2 �����ſ�ʼ����3 ˫���ſ�ʼ��
    /// </summary>
    int keyStart = -1;
    /// <summary>
    /// ��-2 �ѽ�������-1 δ��ʼ������0 δ��ʼ����1 �����ſ�ʼ����2 �����ſ�ʼ����3 ˫���ſ�ʼ��
    /// </summary>
    int valueStart = -1;

    boolean isError = false;//�Ƿ��﷨����

    public void CheckIsError(char c)//ֻ����һ��������ΪGetLength��ݹ鵽ÿһ�������
    {
        switch (c)
        {
            case '\r':
            case '\n':
            case '\t':
                return;
            case '{'://[{ "[{A}]":[{"[{B}]":3,"m":"C"}]}]
                isError = jsonStart && keyValueState == 0;//�ظ���ʼ���� ͬʱ����ֵ����
                break;
            case '}':
                isError = !jsonStart || (keyStart > 0 && keyValueState == 0);//�ظ��������� ���� ��ǰ������
                if (!isError && isStrictMode)
                {
                    isError = !((keyStart == 3 && keyValueState == 0) || (valueStart != 2 && keyValueState == 1) || valueStart == -2 || (jsonStart && keyStart == -1));
                }
                break;
            case '[':
                isError = arrayStart && keyValueState == 0;//�ظ���ʼ����
                break;
            case ']':
                isError = (!arrayStart && valueStart != 3 && keyStart != 3) || (keyValueState == 1 && valueStart == 0);//�ظ���ʼ����[{},]1,0  ������[111,222] 1,1 [111,"22"] 1,-2 
                break;
            case '"':
                isError = !jsonStart && !arrayStart;//δ��ʼJson��ͬʱҲδ��ʼ���顣
                break;
            case '\'':
                isError = (!jsonStart && !arrayStart);//δ��ʼJson
                if (!isError && isStrictMode)
                {
                    isError = !((keyStart == 3 && keyValueState == 0) || (valueStart == 3 && keyValueState == 1));
                }
                break;
            case ':':
                isError = (!jsonStart && !arrayStart) || (jsonStart && keyStart < 2 && valueStart < 2 && keyValueState == 1);//δ��ʼJson ͬʱ ֻ�ܴ�����ȡֵ֮ǰ��
                break;
            case ',':
                isError = (!jsonStart && !arrayStart)
                    || (!jsonStart && arrayStart && keyValueState == -1) //[,111]
                    || (jsonStart && keyStart < 2 && valueStart < 2 && keyValueState == 0);//δ��ʼJson ͬʱ ֻ�ܴ�����ȡֵ֮��
                break;
            //case 't'://true
            //case 'f'://false

            //  break;
            default: //ֵ��ͷ����
                isError = (!jsonStart && !arrayStart) || (keyStart == 0 && valueStart == 0 && keyValueState == 0);//
                if (!isError && keyStart < 2)
                {
                    //if ((jsonStart && !arrayStart) && state != 1)
                    if (jsonStart && keyValueState <= 0)//ȡ���׶�
                    {
                        //�������ſ�ͷ�ģ�ֻ������ĸ {aaa:1}
                        isError = isStrictMode || (c < 65 || (c > 90 && c < 97) || c > 122);
                    }
                    else if (!jsonStart && arrayStart && valueStart < 2)//
                    {
                        switch (c)
                        {
                            case ' ':
                            case 'n'://null
                            case 'u':
                            case 'l':
                            case 't'://true
                            case 'r':
                            case 'e':
                            case 'f'://false
                            case 'a':
                            case 's':
                                break;
                            default:
                                //�������ſ�ͷ�ģ�ֻ��������[1] �ո�null,true,false
                                isError = c < 48 || c > 57;
                                break;
                        }

                    }
                }
                if (!isError && isStrictMode)
                {
                    if (jsonStart && valueStart == 1)//���ֵvalue:true ��value:false
                    {
                        switch (c)
                        {
                            case 'r'://true
                                isError = lastChar != 't';
                                break;
                            case 'u'://true,null
                                isError = !((lastKeywordChar == 't' && lastChar == 'r') || (lastKeywordChar == 'n' && lastChar == 'n'));
                                break;
                            case 'e'://true
                                isError = !((lastKeywordChar == 't' && lastChar == 'u') || (lastKeywordChar == 'f' && lastChar == 's'));
                                break;
                            case 'a'://false
                                isError = lastChar != 'f';
                                break;
                            case 'l'://false,null 
                                isError = !((lastKeywordChar == 'f' && lastChar == 'a') || (lastKeywordChar == 'n' && (lastChar == 'u' || lastChar == 'l')));
                                if (!isError && lastKeywordChar == 'n' && lastChar == 'l')
                                {
                                    //ȡ���ؼ��֣�������� nulllll���l
                                    lastKeywordChar = ' ';
                                }
                                break;
                            case 's'://false
                                isError = lastChar != 'l';
                                break;
                            case '.'://���ֿ��Գ���С���㣬�������ظ�����
                                isError = keyValueState != 1 || lastKeywordChar == '.';
                                break;
                            case ' ':
                                if (lastChar == '.') { isError = true; }
                                else if (jsonStart && !arrayStart)
                                {
                                    valueStart = -2;//�����ո񣬽���ȡֵ��
                                }
                                break;
                            default:
                                //�������ſ�ͷ�ģ�ֻ��������[1]
                                isError = c < 48 || c > 57;
                                break;
                        }
                    }
                    //ֵ��ͷ�ģ�ֻ���ǣ�["xxx"] {[{}]
                }
                break;
        }
        if (isError)
        {
            //
        }
    }

    /// <summary>
    /// �����ַ�״̬(����true��Ϊ�ؼ��ʣ�����false��Ϊ��ͨ�ַ�����
    /// </summary>
    public boolean IsKeyword(char c)
    {
    	boolean isKeyword = false;
        switch (c)
        {
            case '{'://[{ "[{A}]":[{"[{B}]":3,"m":"C"}]}]
                if (keyStart <= 0 && valueStart <= 0)
                {
                    if (jsonStart && keyValueState == 1)
                    {
                        valueStart = 0;
                        childrenStart = true;
                    }
                    else
                    {
                        keyValueState = 0;
                    }
                    jsonStart = true;//��ʼ��
                    isKeyword = true;
                }
                break;
            case '}':
                if (lastChar != '.')
                {
                    if (keyStart <= 0 && valueStart < 2)
                    {
                        if (jsonStart)
                        {
                            jsonStart = false;//����������
                            valueStart = -1;
                            keyValueState = 0;
                            setDicValue = true;
                        }
                        isKeyword = true;
                    }
                }
                break;
            case '[':
                if (!jsonStart)
                {
                    arrayStart = true;
                    isKeyword = true;
                }
                else if (jsonStart && keyValueState == 1 && valueStart < 2)
                {
                    childrenStart = true;
                    isKeyword = true;
                }
                break;
            case ']':
                if (lastChar != '.')
                {
                    if (!jsonStart && (keyStart <= 0 && valueStart <= 0) || (keyStart == -1 && valueStart == 1))
                    {
                        if (arrayStart)// && !childrenStart
                        {
                            arrayStart = false;
                        }
                        isKeyword = true;
                    }
                }
                break;
            case '"':
            case '\'':
                // CheckIsError(c);
                if (isStrictMode && c == '\'')
                {
                    break;
                }
                if (jsonStart || arrayStart)
                {
                    if (!jsonStart && arrayStart)
                    {
                        keyValueState = 1;//��������飬ֻ��ȡֵ��û��Key������ֱ������0
                    }
                    if (keyValueState == 0)//key�׶�
                    {
                        keyStart = (keyStart <= 0 ? (c == '"' ? 3 : 2) : -2);
                        isKeyword = true;
                    }
                    else if (keyValueState == 1)//ֵ�׶�
                    {
                        if (valueStart <= 0)
                        {
                            valueStart = (c == '"' ? 3 : 2);
                            isKeyword = true;
                        }
                        else if ((valueStart == 2 && c == '\'') || (valueStart == 3 && c == '"'))
                        {
                            if (!escapeChar)
                            {
                                valueStart = -2;
                                isKeyword = true;
                            }
                            else
                            {
                                escapeChar = false;
                            }
                        }

                    }
                }
                break;
            case ':':
                // CheckIsError(c);
                if (jsonStart && keyStart < 2 && valueStart < 2 && keyValueState == 0)
                {
                    keyStart = -2;//0 ����key
                    keyValueState = 1;
                    isKeyword = true;
                }
                break;
            case ',':
                if (lastChar != '.')
                {
                    if (jsonStart && keyStart < 2 && valueStart < 2 && keyValueState == 1)
                    {
                        keyValueState = 0;
                        valueStart = 0;
                        setDicValue = true;
                        isKeyword = true;
                    }
                    else if (arrayStart && !jsonStart) //[a,b]  [",",33] [{},{}]
                    {
                        if ((keyValueState == -1 && valueStart == -1) || (valueStart < 2 && keyValueState == 1))
                        {
                            valueStart = 0;
                            isKeyword = true;
                        }
                    }
                }
                break;
            case ' ':
            case '\r':
            case '\n':
            case '\t':
                if (jsonStart && keyStart <= 1 && valueStart <= 1)
                {
                    isKeyword = true;
                    // return true;//�����ո�
                }
                break;
            case 't'://true
            case 'f'://false
            case 'n'://null
            case '-'://-388.8 //�������ַ���
                if (lastKeywordChar != c && lastKeywordChar != '.')
                {
                    if (valueStart <= 1 && ((arrayStart && !jsonStart && keyStart == -1) || (jsonStart && keyValueState == 1 && valueStart <= 0)))
                    {
                        //ֻ��״̬�����ǹؼ���
                        valueStart = 1;
                        lastChar = c;
                        lastKeywordChar = c;
                        return false;//ֱ�ӷ��أ���������
                    }
                }
                break;
            case '.':
                if ((jsonStart || arrayStart) && keyValueState == 1 && valueStart == 1 && lastKeywordChar != c)
                {
                    lastChar = c;
                    lastKeywordChar = c;//��¼���ţ�����ֻ����һ�����š�
                    return false;//��������
                }
                break;
            default: //ֵ��ͷ����
                // CheckIsError(c);
                if (c == '\\') //ת�����
                {
                    if (escapeChar)
                    {
                        escapeChar = false;
                    }
                    else
                    {
                        escapeChar = true;
                    }
                }
                if (jsonStart)
                {
                    if (keyStart <= 0 && keyValueState <= 0)
                    {
                        keyStart = 1;//�����ŵ�
                    }
                    else if (valueStart <= 0 && keyValueState == 1)
                    {
                        valueStart = 1;//�����ŵ�
                    }
                }
                else if (arrayStart)
                {
                    keyValueState = 1;
                    if (valueStart < 1)
                    {
                        valueStart = 1;//�����ŵ�
                    }
                }
                break;
        }
        if (escapeChar && c != '\\')
        {
            escapeChar = false;
        }
        if (!isKeyword)
        {
            CheckIsError(c);
        }
        else
        {
            lastKeywordChar = c;
        }
        lastChar = c;
        return isKeyword;
    }
}