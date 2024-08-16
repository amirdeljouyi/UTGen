#include <assert.h>
#include <jawt_md.h>
#include <jawt.h>
#include <winuser.h>
#include "com_lts_ui_menuflash_MenuBarFlash.h"

JNIEXPORT void JNICALL
Java_com_lts_ui_menuflash_MenuBarFlash_flash(
JNIEnv * env, jobject this, jobject window, jint flashCount, jint flashTime)
{
	
	JAWT awt;
	JAWT_DrawingSurface* ds;
	JAWT_DrawingSurfaceInfo* dsi;
	JAWT_Win32DrawingSurfaceInfo* dsi_win;
	

	jboolean result;
	jint lock;
	FLASHWINFO info;

	// Get the AWT
	awt.version = JAWT_VERSION_1_4;
	result = JAWT_GetAWT(env, &awt);
	assert(result != JNI_FALSE);
	
	// Get the drawing surface
	ds = awt.GetDrawingSurface(env, window);
	if (0 == ds)
		return;
	
	lock = ds->Lock(ds);	
	assert((lock & JAWT_LOCK_ERROR) == 0);
	
	// Get the drawing surface info
	dsi = ds->GetDrawingSurfaceInfo(ds);

	// Get the platform-specific drawing info
	dsi_win = (JAWT_Win32DrawingSurfaceInfo*)dsi->platformInfo;

	info.cbSize = sizeof(FLASHWINFO);
	info.hwnd = dsi_win->hwnd;
	info.dwFlags = 0002;
	info.uCount = flashCount;
	info.dwTimeout = flashTime;

	FlashWindowEx(&info);
	ds->Unlock(ds);
}
