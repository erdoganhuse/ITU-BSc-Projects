using UnityEngine;
using System.Collections;

public class LoadSmallGame : MonoBehaviour {
	public int smallGameType;
	public int smallGameLevel;

	void OnTriggerEnter(Collider other) {
		print ("smallGameType: " + smallGameType);
	}
}
