#JDBCCouponProject


#For this application to run you HAVE to do several things first:#
1.Have JDK installed on your machine

2.Have derby installed on your machine

3.Start the derby DB client (preferable as administrator)


#Credentials:#

Admin:
admin | 1234

Company:
                Company(1, "Intel", "1234abc", "intel@intel.com")
                Company(2, "Qualitest", "1234abc", "Melbourne@liquid.com")
                Company(3, "SigmaDesigns", "1234AbC", "Mel34@liquid.com")
                Company(4, "Apple", "1235", "appleCrack@gsdgs.com")
                Company(5, "McGrew-Hill Education", "MHCampus", "man@oop.com")
                Company(6, "Adidas", "123", "jjj@kkk.com")
                Company(7, "Nike", "123", "jjj@66hy.com")
                Company(8, "OrCorp", "123", "jjj@cvbft.com")

Customer:
                Customer(1, "kowalsky", "1234");
                Customer(2, "Greg", "12345"));
                Customer(3, "Robert", "12345"));
                Customer(4, "Eric", "12345"));
                Customer(5, "Lisa", "12345"));
                Customer(6, "Tony", "12345"));
                Customer(7, "Stephen", "12345"));
                Customer(8, "Kevin", "12345"));

Coupon:
                Coupon(1, "Holiday", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"),300,
                        CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");
                Coupon(2, "Food", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
                        CouponType.RESTAURANT, "random coupon message", 149.99, "pathToImage/image.jpeg");
                Coupon(3, "Electricity", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"),
                        300, CouponType.ELECTRICITY, "random coupon message", 149.99, "pathToImage/image.jpeg");
                Coupon(4, "Sport", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
                        CouponType.SPORTS, "random coupon message", 149.99, "pathToImage/image.jpeg");
                Coupon(5, "Homedesign", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"),
                        300, CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");

As you'll progress with the application, you would be able to see different abilities, according to the client type.

You would be able to experience all 3 types. Up to you!
