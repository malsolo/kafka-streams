/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.malsolo.kafka.purchase.model.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class CorrelatedPurchase extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4405993311229722839L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CorrelatedPurchase\",\"namespace\":\"com.malsolo.kafka.purchase.model.avro\",\"fields\":[{\"name\":\"customerId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"itemsPurchased\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}},{\"name\":\"totalAmount\",\"type\":\"double\"},{\"name\":\"firstPurchaseTime\",\"type\":{\"type\":\"int\",\"logicalType\":\"date\"}},{\"name\":\"secondPurchaseTime\",\"type\":{\"type\":\"int\",\"logicalType\":\"date\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();
static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.DateConversion());
  }

  private static final BinaryMessageEncoder<CorrelatedPurchase> ENCODER =
      new BinaryMessageEncoder<CorrelatedPurchase>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CorrelatedPurchase> DECODER =
      new BinaryMessageDecoder<CorrelatedPurchase>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CorrelatedPurchase> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CorrelatedPurchase> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CorrelatedPurchase> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CorrelatedPurchase>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CorrelatedPurchase to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CorrelatedPurchase from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CorrelatedPurchase instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CorrelatedPurchase fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String customerId;
   private java.util.List<java.lang.String> itemsPurchased;
   private double totalAmount;
   private java.time.LocalDate firstPurchaseTime;
   private java.time.LocalDate secondPurchaseTime;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CorrelatedPurchase() {}

  /**
   * All-args constructor.
   * @param customerId The new value for customerId
   * @param itemsPurchased The new value for itemsPurchased
   * @param totalAmount The new value for totalAmount
   * @param firstPurchaseTime The new value for firstPurchaseTime
   * @param secondPurchaseTime The new value for secondPurchaseTime
   */
  public CorrelatedPurchase(java.lang.String customerId, java.util.List<java.lang.String> itemsPurchased, java.lang.Double totalAmount, java.time.LocalDate firstPurchaseTime, java.time.LocalDate secondPurchaseTime) {
    this.customerId = customerId;
    this.itemsPurchased = itemsPurchased;
    this.totalAmount = totalAmount;
    this.firstPurchaseTime = firstPurchaseTime;
    this.secondPurchaseTime = secondPurchaseTime;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return customerId;
    case 1: return itemsPurchased;
    case 2: return totalAmount;
    case 3: return firstPurchaseTime;
    case 4: return secondPurchaseTime;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      new org.apache.avro.data.TimeConversions.DateConversion(),
      new org.apache.avro.data.TimeConversions.DateConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: customerId = value$ != null ? value$.toString() : null; break;
    case 1: itemsPurchased = (java.util.List<java.lang.String>)value$; break;
    case 2: totalAmount = (java.lang.Double)value$; break;
    case 3: firstPurchaseTime = (java.time.LocalDate)value$; break;
    case 4: secondPurchaseTime = (java.time.LocalDate)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'customerId' field.
   * @return The value of the 'customerId' field.
   */
  public java.lang.String getCustomerId() {
    return customerId;
  }


  /**
   * Sets the value of the 'customerId' field.
   * @param value the value to set.
   */
  public void setCustomerId(java.lang.String value) {
    this.customerId = value;
  }

  /**
   * Gets the value of the 'itemsPurchased' field.
   * @return The value of the 'itemsPurchased' field.
   */
  public java.util.List<java.lang.String> getItemsPurchased() {
    return itemsPurchased;
  }


  /**
   * Sets the value of the 'itemsPurchased' field.
   * @param value the value to set.
   */
  public void setItemsPurchased(java.util.List<java.lang.String> value) {
    this.itemsPurchased = value;
  }

  /**
   * Gets the value of the 'totalAmount' field.
   * @return The value of the 'totalAmount' field.
   */
  public double getTotalAmount() {
    return totalAmount;
  }


  /**
   * Sets the value of the 'totalAmount' field.
   * @param value the value to set.
   */
  public void setTotalAmount(double value) {
    this.totalAmount = value;
  }

  /**
   * Gets the value of the 'firstPurchaseTime' field.
   * @return The value of the 'firstPurchaseTime' field.
   */
  public java.time.LocalDate getFirstPurchaseTime() {
    return firstPurchaseTime;
  }


  /**
   * Sets the value of the 'firstPurchaseTime' field.
   * @param value the value to set.
   */
  public void setFirstPurchaseTime(java.time.LocalDate value) {
    this.firstPurchaseTime = value;
  }

  /**
   * Gets the value of the 'secondPurchaseTime' field.
   * @return The value of the 'secondPurchaseTime' field.
   */
  public java.time.LocalDate getSecondPurchaseTime() {
    return secondPurchaseTime;
  }


  /**
   * Sets the value of the 'secondPurchaseTime' field.
   * @param value the value to set.
   */
  public void setSecondPurchaseTime(java.time.LocalDate value) {
    this.secondPurchaseTime = value;
  }

  /**
   * Creates a new CorrelatedPurchase RecordBuilder.
   * @return A new CorrelatedPurchase RecordBuilder
   */
  public static com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder newBuilder() {
    return new com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder();
  }

  /**
   * Creates a new CorrelatedPurchase RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CorrelatedPurchase RecordBuilder
   */
  public static com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder newBuilder(com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder other) {
    if (other == null) {
      return new com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder();
    } else {
      return new com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder(other);
    }
  }

  /**
   * Creates a new CorrelatedPurchase RecordBuilder by copying an existing CorrelatedPurchase instance.
   * @param other The existing instance to copy.
   * @return A new CorrelatedPurchase RecordBuilder
   */
  public static com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder newBuilder(com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase other) {
    if (other == null) {
      return new com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder();
    } else {
      return new com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder(other);
    }
  }

  /**
   * RecordBuilder for CorrelatedPurchase instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CorrelatedPurchase>
    implements org.apache.avro.data.RecordBuilder<CorrelatedPurchase> {

    private java.lang.String customerId;
    private java.util.List<java.lang.String> itemsPurchased;
    private double totalAmount;
    private java.time.LocalDate firstPurchaseTime;
    private java.time.LocalDate secondPurchaseTime;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.customerId)) {
        this.customerId = data().deepCopy(fields()[0].schema(), other.customerId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.itemsPurchased)) {
        this.itemsPurchased = data().deepCopy(fields()[1].schema(), other.itemsPurchased);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.totalAmount)) {
        this.totalAmount = data().deepCopy(fields()[2].schema(), other.totalAmount);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.firstPurchaseTime)) {
        this.firstPurchaseTime = data().deepCopy(fields()[3].schema(), other.firstPurchaseTime);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.secondPurchaseTime)) {
        this.secondPurchaseTime = data().deepCopy(fields()[4].schema(), other.secondPurchaseTime);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
    }

    /**
     * Creates a Builder by copying an existing CorrelatedPurchase instance
     * @param other The existing instance to copy.
     */
    private Builder(com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.customerId)) {
        this.customerId = data().deepCopy(fields()[0].schema(), other.customerId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.itemsPurchased)) {
        this.itemsPurchased = data().deepCopy(fields()[1].schema(), other.itemsPurchased);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.totalAmount)) {
        this.totalAmount = data().deepCopy(fields()[2].schema(), other.totalAmount);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.firstPurchaseTime)) {
        this.firstPurchaseTime = data().deepCopy(fields()[3].schema(), other.firstPurchaseTime);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.secondPurchaseTime)) {
        this.secondPurchaseTime = data().deepCopy(fields()[4].schema(), other.secondPurchaseTime);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'customerId' field.
      * @return The value.
      */
    public java.lang.String getCustomerId() {
      return customerId;
    }


    /**
      * Sets the value of the 'customerId' field.
      * @param value The value of 'customerId'.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder setCustomerId(java.lang.String value) {
      validate(fields()[0], value);
      this.customerId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'customerId' field has been set.
      * @return True if the 'customerId' field has been set, false otherwise.
      */
    public boolean hasCustomerId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'customerId' field.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder clearCustomerId() {
      customerId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'itemsPurchased' field.
      * @return The value.
      */
    public java.util.List<java.lang.String> getItemsPurchased() {
      return itemsPurchased;
    }


    /**
      * Sets the value of the 'itemsPurchased' field.
      * @param value The value of 'itemsPurchased'.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder setItemsPurchased(java.util.List<java.lang.String> value) {
      validate(fields()[1], value);
      this.itemsPurchased = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'itemsPurchased' field has been set.
      * @return True if the 'itemsPurchased' field has been set, false otherwise.
      */
    public boolean hasItemsPurchased() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'itemsPurchased' field.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder clearItemsPurchased() {
      itemsPurchased = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'totalAmount' field.
      * @return The value.
      */
    public double getTotalAmount() {
      return totalAmount;
    }


    /**
      * Sets the value of the 'totalAmount' field.
      * @param value The value of 'totalAmount'.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder setTotalAmount(double value) {
      validate(fields()[2], value);
      this.totalAmount = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'totalAmount' field has been set.
      * @return True if the 'totalAmount' field has been set, false otherwise.
      */
    public boolean hasTotalAmount() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'totalAmount' field.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder clearTotalAmount() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'firstPurchaseTime' field.
      * @return The value.
      */
    public java.time.LocalDate getFirstPurchaseTime() {
      return firstPurchaseTime;
    }


    /**
      * Sets the value of the 'firstPurchaseTime' field.
      * @param value The value of 'firstPurchaseTime'.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder setFirstPurchaseTime(java.time.LocalDate value) {
      validate(fields()[3], value);
      this.firstPurchaseTime = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'firstPurchaseTime' field has been set.
      * @return True if the 'firstPurchaseTime' field has been set, false otherwise.
      */
    public boolean hasFirstPurchaseTime() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'firstPurchaseTime' field.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder clearFirstPurchaseTime() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'secondPurchaseTime' field.
      * @return The value.
      */
    public java.time.LocalDate getSecondPurchaseTime() {
      return secondPurchaseTime;
    }


    /**
      * Sets the value of the 'secondPurchaseTime' field.
      * @param value The value of 'secondPurchaseTime'.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder setSecondPurchaseTime(java.time.LocalDate value) {
      validate(fields()[4], value);
      this.secondPurchaseTime = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'secondPurchaseTime' field has been set.
      * @return True if the 'secondPurchaseTime' field has been set, false otherwise.
      */
    public boolean hasSecondPurchaseTime() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'secondPurchaseTime' field.
      * @return This builder.
      */
    public com.malsolo.kafka.purchase.model.avro.CorrelatedPurchase.Builder clearSecondPurchaseTime() {
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CorrelatedPurchase build() {
      try {
        CorrelatedPurchase record = new CorrelatedPurchase();
        record.customerId = fieldSetFlags()[0] ? this.customerId : (java.lang.String) defaultValue(fields()[0]);
        record.itemsPurchased = fieldSetFlags()[1] ? this.itemsPurchased : (java.util.List<java.lang.String>) defaultValue(fields()[1]);
        record.totalAmount = fieldSetFlags()[2] ? this.totalAmount : (java.lang.Double) defaultValue(fields()[2]);
        record.firstPurchaseTime = fieldSetFlags()[3] ? this.firstPurchaseTime : (java.time.LocalDate) defaultValue(fields()[3]);
        record.secondPurchaseTime = fieldSetFlags()[4] ? this.secondPurchaseTime : (java.time.LocalDate) defaultValue(fields()[4]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CorrelatedPurchase>
    WRITER$ = (org.apache.avro.io.DatumWriter<CorrelatedPurchase>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CorrelatedPurchase>
    READER$ = (org.apache.avro.io.DatumReader<CorrelatedPurchase>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










