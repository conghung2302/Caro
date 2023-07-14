package Core;

public class Result 
{
    public String mActionType;
    public String mResultCode;
    public String mContent;

    public Result(String mActionType, String mResultCode, String mContent) {
        this.mActionType = mActionType;
        this.mResultCode = mResultCode;
        this.mContent = mContent;
    }
}
