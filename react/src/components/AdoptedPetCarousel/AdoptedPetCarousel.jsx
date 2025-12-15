// import "slick-carousel/slick/slick.css";
// import "slick-carousel/slick/slick-theme.css";
// import Slider from "react-slick";


/* 
  This will make EVERYTHING on the display part of a slider.
  Primary goal is to figure out how to make only PART of the Homepage a slideshow.
  Backup goal:  Make a second view specifically for adopted pets that will slide through the photos.

  If we use a secondary view, set a grid and specifically assign a part of the grid to be like a slideshow.
  Specifically label a CLASS for the slideshow view and use <AdoptedPetCarousel /> to mark it. 
  
  From what I can tell now it seems difficult to display the info I want to without breaking the whole page,
  Focus on making the adopted pets like CatCards to display a single random Pet with the approved status each
  time the page is refreshed, if I have extra time I will then attempt to style the carousel to be functional. 
*/


// export default function AdoptedPetCarousel() {
//   var settings = {
//     dots: true,
//     infinite: true,
//     speed: 500,
//     slidesToShow: 1,
//     slidesToScroll: 1,
//   };

//    return (
//     <Slider {...settings}>
//       <div>
//         <h3>1</h3>
//       </div>
//       <div>
//         <h3>2</h3>
//       </div>
//       <div>
//         <h3>3</h3>
//       </div>
//     </Slider>
//   );
// }