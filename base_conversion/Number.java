
/**
 * The {@code Number} class represents integers with their bases.
 * 
 * <p>
 * The class {@code Number} includes in addition to some utilitarian methods
 * a method for verifying the correlation between value and base of given
 * numbers and another that allows to convert them from one base to another.
 * </p>
 * 
 * @author Al Bebeto Toengaho
 * @version 1.0
 * @since 2018/02/22
 */
public class Number {
    
    private static final int DECIMAL_BASE = 10;
    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private char[] value;
    private int base;

    /**
     * Constructs a new {@code Number} object.
     */
    public Number() {
    }

    /**
     * Initializes a newly created {@code Number} object so that it represents
     * the same number as the argument; in other words, the
     * newly created number is a copy of the argument number.
     * 
     * @param number a number object
     */
    public Number(Number number) {
        this.value = number.value;
        this.base = number.base;
    }
    
    /**
     * Constructs a new {@code Number} object with the specified base
     * and number value.
     * 
     * @param base the number's base
     * @param value the number's value
     */
    public Number(int base, String value) throws NumberException {
        
        if(base < 2 || base > 36) {
            throw new NumberException(
                    "The base must be greater than 1 and least than 36.");   
        }
        
        if(!verifyValue(base, value.toCharArray())) {
            throw new NumberException(
                    "The value and the base don't fit together.");  
        }
        
        this.base = base;
        this.value = value.toCharArray();
    }

    /**
     * 
     * @param base
     * @param digits
     * @return 
     */
    private boolean verifyValue(int base, char[] digits) {
        
        for(char digit : digits) {
            if(DIGITS.indexOf(digit) >= base)
                return false;
        }
        return true;
    }
    
    public String getValue() {
        return new String(value);
    }

    public void setValue(String value) throws NumberException {
        
        if(!verifyValue(base, value.toCharArray())) {
            throw new NumberException(
                    "The value and the base don't fit together.");  
        }
        this.value = value.toCharArray();
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        
        if(base < 2 || base > 36) {
            throw new NumberException(
                    "The base must be greater than 1 and least than 36.");   
        }
        this.base = base;
    }
    
    /**
     * Converts the current number into decimal base.
	 *
     * @return The corresponding {@code Number} object in decimal base.
     */
    private Number convertToDecimal() {
        
        if(base == DECIMAL_BASE)
            return new Number(this);
        
        int length = value.length;
        long res = 0;
        int charValue;
        for(int i = 0; i < length; i++) {
            charValue = DIGITS.indexOf(value[i]);
            res += (charValue*Math.pow(base, length-(i+1)));
        }
        
        return new Number(DECIMAL_BASE, String.valueOf(res));
    }
    
    /**
     * Converts the current number into given base.
	 *
     * @param outBase The base to convert to.
	 *
	 * @return The corresponding {@code Number} object in the given base.
     */
    public Number convertToBase(int outBase) {
        
        if(outBase == base)
            return new Number(this);
        if(outBase == DECIMAL_BASE)
            return convertToDecimal();
        
        String res = "";
        long valueToConvert = Long.valueOf(convertToDecimal().getValue());
        
        do {
            int rem = (int)(valueToConvert % outBase);
            res = DIGITS.charAt(rem) + res;
            valueToConvert /= outBase; 
        } while(valueToConvert >= outBase);
        
        if(valueToConvert != 0)
            res = DIGITS.charAt((int)valueToConvert) + res;
              
        return new Number(outBase, res);
    }
    
    @Override
    public String toString() {
        return new String(value) + "(" + base + ")";
    }
	
	// A main method to do some tests.
    public static void main(String[] args) {
        
        Number n16 = null;
        Number n10a = null;
        Number n35 = null;
        Number n8 = null;
        Number n10b = null;
        
        try {
            n16 = new Number(36, "ZZZZZZ");
            n10a = n16.convertToDecimal();
            n35 = n10a.convertToBase(35);
            n8 = n35.convertToBase(8);
            n10b = n8.convertToDecimal();
            
            System.out.println(n16 + " -> " + n10a + " -> " + n35 + " -> " 
                                + n8 + " -> " + n10b);
        } catch(NumberException exc) {
            System.out.println(exc.getMessage());
        }
    }
}