package au.com.tyo.android;

public interface Android {
	
	public static final String OBB_NAME_TEMPLATE = "%s.%d.%s.obb"; 
	
	public boolean hasVoiceRecognitionService();

	String getAppDataDataPath();

	String getAppDataSubPath(String subPath);

	String getPatchObbName(int expansionVersion);

	String getObbName(String mainOrPatch, int expansionVersion);

	String getMainObbName(int expansionVersion);

	String getAppObbPath();

	String getAppDataPath();
	
}
