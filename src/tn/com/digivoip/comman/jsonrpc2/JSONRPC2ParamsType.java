package tn.com.digivoip.comman.jsonrpc2;

/** Enumeration of the three parameter types in JSON-RPC 2.0 requests and notifications.
 * <ul>
 * <li>{@link #NO_PARAMS} The method takes no parameters.
 * <li>{@link #ARRAY} The method takes positional parameters, packed as a JSON array, e.g. {@code ["val1", "val2", ...]}.
 * <li>{@link #OBJECT} The method takes named parameters, packed as a JSON object, e.g. {@code "param1":"val1", "param2":"val2", ...} .
 * </ul>
 * <p>
 * The JSON-RPC 2.0 specification and user group forum can be found <a href="http://groups.google.com/group/json-rpc">here</a>.
 * @author Vladimir Dzhuvinov
 * @version 1.34.3 (2012-11-27) */
public enum JSONRPC2ParamsType {
	/** No parameters. */
	NO_PARAMS,
	/** Positional parameters, packed as a JSON array. */
	ARRAY,
	/** Named parameters, packed as a JSON object. */
	OBJECT
}
