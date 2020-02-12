/**
(1) Name: Dakota Morgan
(2) Date: 10/21/2019
(3) Instructor: Ms Tucker
(4) Class: CIT249 Java II
(5) Purpose: Use recursion to explore a file structure, encrypt the file and folder names.
**/

import java.io.File;

public class FileNameEncryption implements Encryptable
{

	/* ---------------------------------------------------------------------------
	 *	Class Data:
	 *
	 *  fullDirectory:   Formattted output which represents the directory structure
	 *	directorList[ ]: Array of File objects for the directory structure
	 * --------------------------------------------------------------------------*/

  private String fullDirectory = "";
  private File directoryList[ ];
  private boolean isEncrypted;

  private String fileType;

  /*
  * initialize indent
  */
  private String indent = "";


  private static final char[] key = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N'
  ,'O','P','Q','R','S','T','U','V','W','X','Y','Z',' ','0','1','2','3','4','5','6','7','8','9','[',']','.'};

  private static final char[] cipher = {'C','O','P','Y','R','I','G','H','T','E','D',' ','2','0'
  ,'1','9','A','B','F','J','K','L','M','N','Q','S','U','V','W','X','Z','3','4','5','6','7','8','[',']','.'};

	/* ---------------------------------------------------------------------------
	 *	THIS IS A RECURSIVE METHOD - DO NOT CHANGE THE PARAMETERS
	 *	OR RETURNED DATA TYPE.
	 *
	 *  RECUSION IS NOT USED FOR FILES IN THE STRUCURE - ONLY FOLDER PROCESSING
	 *  IS RECURSIVE.
	 *
	 *  Parameters:
	 *		directory: A File object which is an array of File objects (files and folders)
	 *	    level:     Used to tab at the appropriate level in the display.
	 * --------------------------------------------------------------------------*/

  private void createFullDirectory(File[] directory, int index)
  {
   /*
   * if index reaches directory length, done
   */
      if(index >= directory.length){
       	return;
      }
      else{
      /*
      * if object at index is file, print file name
      */
      if(directory[index].isFile()){
        fullDirectory += indent + "FILE:\t" + directory[index].getName() + "\n";
      }
      /*
      * if object at index is directory, print directory name
      */
      else{
        fullDirectory += indent + "FOLDER:\t[" + directory[index].getName() + "]\n";
        /*
        * add an indention
        */
        indent += "     ";
        /*
        * explore directory with recursion
        */
        createFullDirectory(directory[index].listFiles(), 0);
        /*
        * remove indention
        */
        indent = indent.substring(0, indent.length() - 5);
      }
      /*
      * check next index
      */
      createFullDirectory(directory, index + 1);
  }
}



	 /* ---------------------------------------------------------------------------
	 *	Class constructor
	 *
	 *	The directory is validated and then a File array is created if valid.
	 *  The createFullDirectory method is called to create the formatted display of
	 *  of the directory.
	 * --------------------------------------------------------------------------*/

  public FileNameEncryption(String path)
  {

		if(path != null && path != "")
		{
			File maindir = new File(path);

			if	(  maindir.exists() && maindir.isDirectory()  )
			{	directoryList = maindir.listFiles();
				fullDirectory = "";
				createFullDirectory(directoryList, 0);
				isEncrypted = false;
			}
		}
	}

	 /* ---------------------------------------------------------------------------
	 *	Accessors for fullDirectory and isEncrypted
	 * --------------------------------------------------------------------------*/

	public String getFullDirectory()
	{
		return fullDirectory;
	}
	public boolean directoryIsEncrypted()
	{
		return isEncrypted;
	}
	 /* ---------------------------------------------------------------------------
	 *	encrypt method for fullDirectory
	 * --------------------------------------------------------------------------*/

   public final void encrypt()
   {
       /**
        * Replace each character in string with cipher[] character equivalent
        */
       if (!directoryIsEncrypted()){
           String masked = "";
           int index = 0;
           boolean eReady = false;
           boolean lineDone = false;
           boolean found = false;
           /*
           * loop through string one index at a time
           */
           while(index < fullDirectory.length()){
              /*
              * Do operations for one line at a time
              */
              while(lineDone != true){
                /*
                * if new line is encountered, line is over
                */
                if(fullDirectory.charAt(index) == '\n'){
                  lineDone = true;
                }
                /*
                * if colon is encountered, rest of line is ready to be encrypted, add colon to string
                */
                else if(fullDirectory.charAt(index) == ':' && !eReady){
                  eReady = true;
                  masked = masked + fullDirectory.charAt(index);
                }
                /*
                * record text before colon unencrypted
                */
                else if(!eReady){
                  masked = masked + fullDirectory.charAt(index);
                }
                /*
                * if colon has been encountered, string is ready to be encrypted
                */
                if(eReady){
                    /*
                    * for each char in cipher, check if char at index matches key
                    */
                    for (int j = 0; j < key.length; j++){
                        /*
                        * if index matches key and a char hasn't already been found at this index
                        */
                        if (fullDirectory.charAt(index) == (char) key[j] && !found){
                            /*
                            * add matching cipher char to encrypted string
                            */
                            masked = masked + (char) (cipher[j]);
                            /*
                            * mark char found
                            */
                            found = true;
                        }
                        /*
                        * if index matches lowercase version of key
                        * and a char hasn't already been found at this index
                        */
                        else if (fullDirectory.charAt(index) == Character.toLowerCase(key[j])
                        && !found && Character.isLetter(fullDirectory.charAt(index))){
                            /*
                            * add matching cipher char to encrypted string
                            */
                            masked = masked + Character.toLowerCase(key[j]);
                            /*
                            * mark char found
                            */
                            found = true;
                        }
                    }
                }
                /*
                * increment index, reset found
                */
                index++;
                found = false;
             }
             /*
             * new line, reset eReady and lineDone, add newline
             */
             eReady = false;
             lineDone = false;
             masked = masked + '\n';
           }
           /*
           * set fullDirectory to encrypted string
           */
           fullDirectory = masked;
           isEncrypted = true;
       }
   }


	 /* ---------------------------------------------------------------------------
	 *	decrypt method for fullDirectory
	 *
	 * --------------------------------------------------------------------------*/

   public final String decrypt()
   {
       /**
        * Replace each character in string with key[] character equivalent
        */
        String unmasked = "";
        if (directoryIsEncrypted()){
            int index = 0;
            boolean eReady = false;
            boolean lineDone = false;
            boolean found = false;
            /*
            * loop through string one index at a time
            */
            while(index < fullDirectory.length()){
               /*
               * Do operations for one line at a time
               */
               while(lineDone != true){
                 /*
                 * if new line is encountered, line is over
                 */
                 if(fullDirectory.charAt(index) == '\n'){
                   lineDone = true;
                 }
                 /*
                 * if colon is encountered, rest of line is ready to be encrypted, add colon to string
                 */
                 else if(fullDirectory.charAt(index) == ':' && !eReady){
                   eReady = true;
                   unmasked = unmasked + fullDirectory.charAt(index);
                 }
                 /*
                 * record text before colon unencrypted
                 */
                 else if(!eReady){
                   unmasked = unmasked + fullDirectory.charAt(index);
                 }
                 /*
                 * if colon has been encountered, string is ready to be encrypted
                 */
                 if(eReady){
                     /*
                     * for each char in cipher, check if char at index matches cipher
                     */
                     for (int j = 0; j < cipher.length; j++){
                         if (fullDirectory.charAt(index) == (char) cipher[j] && !found){
                             /*
                             * add matching key char to unencrypted string
                             */
                             unmasked = unmasked + (char) (key[j]);
                             /*
                             * mark char found
                             */
                             found = true;
                         }
                         /*
                         * if index matches lowercase version of key
                         * and a char hasn't already been found at this index
                         */
                         else if (fullDirectory.charAt(index) == Character.toLowerCase(cipher[j])
                         && !found && Character.isLetter(fullDirectory.charAt(index))){
                             /*
                             * add matching key char to unencrypted string
                             */
                             unmasked = unmasked + Character.toLowerCase(cipher[j]);
                             /*
                             * mark char found
                             */
                             found = true;
                         }
                     }
                 }
                 /*
                 * increment index, reset found
                 */
                 index++;
                 found = false;
              }
              /*
              * new line, reset eReady and lineDone, add newline
              */
              eReady = false;
              lineDone = false;
              unmasked = unmasked + '\n';
            }
            /*
            * set fullDirectory to unencrypted string
            */
            isEncrypted = false;
            fullDirectory = unmasked;
        }
        return unmasked;
   }
}
