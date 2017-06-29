using UnityEngine;
using System.Collections;

public class BackgroundRotater : MonoBehaviour {
	public float rotateSpeed = 20f;
	private float angle;

	void Start(){
		angle = transform.eulerAngles.x;
	}

	void Update () {
		angle += rotateSpeed;
		transform.rotation = Quaternion.Euler(angle, 0f, 90f);
	}
}
