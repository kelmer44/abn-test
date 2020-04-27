# abn-test

##  Architecture
- Clean architecture approach
- MVVM using LiveData
- RxJava2 is used on the Model layer
- Koin for dependency injection
- Room for database storage using the repository pattern
- Unit Tests have been provided

#### Notes:
- Foursquare Userless authentication has been used. I assumed this was the requirement since the exercise only mentioned two screens and never a login screen
- A mocked api service is provided to account for quota exceeding (for it is a very limited amount)
- Userless authentication returns only one photo per venue, an additional request to `/venue/{id}/photos` is performed as a showcase to retrieve more, but results are always limited to 1 photo by Foursquare. However a Gallery widget has been implemented to showcase multiple photos. This can be tested by replacing the injected Api Service with the mocked one.
See [https://developer.foursquare.com/docs/announcements/#start-up-tier-launch](https://developer.foursquare.com/docs/announcements/#start-up-tier-launch)
- Only `VenueDetails` is cached since caching search results is not very useful
- Location has been hardcoded to Amsterdam for simplicity