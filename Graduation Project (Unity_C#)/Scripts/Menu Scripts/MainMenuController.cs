using UnityEngine;
using System.Collections;

public class MainMenuController : MonoBehaviour {
	private GestureListener gestureListener;

	void Start () {
		gestureListener = Camera.main.GetComponent<GestureListener>();
	}
	

	void Update () {
		if (gestureListener.IsSwipeLeft()) {
			print ("asdasdasd");
		}
	}
}
