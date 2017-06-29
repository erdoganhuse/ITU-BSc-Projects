typedef enum basket_size_values {small, medium, large} basketSize; 
typedef enum fruit_type_values {standard, citrus, tropical} fruitType;

class Item{
protected:
	double priceofitem;
public:;
	virtual void calculate_price()=0;
	virtual void print() const;
};

class Flower : public Item{
protected:
	bool is_artificial;
public:
	Flower(bool );
	void calculate_price();
	void print() const;
};

class Rose : public Flower{
protected:
	int number;
public:
	Rose(bool ,int );
	void calculate_price();
	void print() const;
};

class Daisy : public Flower{
public:
	Daisy(bool );
	void calculate_price();
	void print() const;
};

class Gourmet : public Item{
protected:
	int basketSize;
	bool promotion;
public: 
	Gourmet(int );
	void calculate_price()=0;
	void print() const;
};

class FruitBasket : public Gourmet{
protected:
	int fruitType;
	bool with_chocolate_sauce;
public: 
	FruitBasket(int ,int ,bool );
	void calculate_price();
	void print() const;
};

class CookieBasket : public Gourmet{
public:
	CookieBasket(int );
	void calculate_price();
	void print() const;
};




