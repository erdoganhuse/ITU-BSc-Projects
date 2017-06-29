using UnityEngine;
using System.Collections;

public class DestroyByBoundary : MonoBehaviour {

	void OnTriggerExit(Collider other)
	{
		if( other.gameObject.transform.parent != null){
			Destroy (other.gameObject.transform.parent.gameObject);
		}
		else {
			Destroy (other.gameObject);
		}
	}

	
}
