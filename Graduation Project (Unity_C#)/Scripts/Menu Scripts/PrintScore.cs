using UnityEngine;
using System.Collections;

public class PrintScore : MonoBehaviour {

	public GUIText scoreText;
	private int score = 0;

	// Use this for initialization
	void Start () {
		scoreText.text = "Score = " + score.ToString();
	}
	
	void OnTriggerEnter(Collider other) 
	{
		score = score + 10;
		scoreText.guiText.text = "Score = " + score.ToString();
	}

}
