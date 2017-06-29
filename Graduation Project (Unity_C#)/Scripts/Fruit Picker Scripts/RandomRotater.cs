using UnityEngine;
using System.Collections;

public class RandomRotater : MonoBehaviour {

	public float tumble;

	void Start () {
		rigidbody.angularVelocity = Random.insideUnitSphere * tumble;	
	}

}
