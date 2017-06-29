using UnityEngine;
using System.Collections;

public class DestroyByContact : MonoBehaviour
{
	public GameObject explosion;
	public GameObject playerExplosion;
	public int score;
	public GUIText scoreText;

	public GameObject myAnimation1;
	public GameObject myAnimation2;

	void Start(){
		score = 0;
	}

	void OnTriggerEnter(Collider other) 
	{
		if (other.tag == "Boundary")
		{
			return;
		}

		if (other.tag == "Rock") {
			//Quaternion spawn = Quaternion.Euler(90f,0f,0f);
			//Instantiate(myAnimation1, transform.position, spawn);
			myAnimation1.animation.Play();
			score = score - 20;
			Instantiate (playerExplosion, other.transform.position, other.transform.rotation);
		} 
		else {
			//Quaternion spawn = Quaternion.Euler(90f,0f,0f);
			//Instantiate(myAnimation1, transform.position, spawn);
			myAnimation2.animation.Play();
			score = score + 10;
			Instantiate(explosion, transform.position, transform.rotation);
		}
		Destroy(other.gameObject);
		scoreText.guiText.text = "Score = " + score.ToString ();
	}
}