package net.doepner.text;

/**
 * A span of text within a larger text body
 */
public interface TextSpan {

    /**
     * @return The offset where the text span starts
     */
    int getStart();

    /**
     * @return The offset where the text span ends
     */
    int getEnd();

    /**
     * @return The content of the text span
     */
    String getContent();

   TextSpan NONE = new TextSpan() {
       @Override
       public int getStart() {
           return -1;
       }

       @Override
       public int getEnd() {
           return -1;
       }

       @Override
       public String getContent() {
           return "";
       }
   };

}
