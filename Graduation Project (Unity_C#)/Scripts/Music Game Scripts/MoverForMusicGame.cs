using UnityEngine;
using System.Collections;

public class MoverForMusicGame : MonoBehaviour {

	public float speed;
	void Start()
	{
		rigidbody.velocity = transform.forward * speed;
	}

}
