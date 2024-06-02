export class Hotel {
  hotelID: number;
  hotelName: string;
  standard: number;
  country: string;
  city: string;
  childrenAllowed: boolean;
  address: string;

  constructor(
    hotelID: number,
    hotelName: string,
    standard: number,
    country: string,
    city: string,
    childrenAllowed: boolean,
    address: string
  ) {
    this.hotelID = hotelID;
    this.hotelName = hotelName;
    this.standard = standard;
    this.country = country;
    this.city = city;
    this.childrenAllowed = childrenAllowed;
    this.address = address;
  }
}
