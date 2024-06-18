package exceptions;

public class EcuapassExceptions {
	public static class ConnectionError extends Exception {
		public ConnectionError (String message) {
			super (message);
		}
	}
}
