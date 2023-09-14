package exception;

public class OperationUnsuccessful extends Exception{

	public OperationUnsuccessful(String message, Exception e) {
		super(message, e);	
	}
	
	public String toString()
	{
		return "Operation not Successful";
	}
	
	

}
